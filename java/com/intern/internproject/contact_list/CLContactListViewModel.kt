package com.intern.internproject.contact_list

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.example.CLLoginResponseToken
import com.example.CLLoginResponseUser
import com.google.gson.Gson
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLContacListUsers1
import com.intern.internproject.services.CLNetworkInterceptor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CLContactListViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    var searchResult = MutableLiveData<ArrayList<CLLoginResponseUser>>()
    var serverSuccess = MutableLiveData<String?>()
    var serverFailure = MutableLiveData<String?>()
    val followSuccess = MutableLiveData<String?>()
    val followFail = MutableLiveData<String?>()
    val searchSuccess by lazy { MutableLiveData<List<CLContacListUsers1?>>() }
    val searchFail by lazy { MutableLiveData<String?>() }

    fun followRequest(userId: Int) {
        val token = retrieveToken()
        if (!CLNetworkInterceptor().isInternetAvailable()) {
            followFail.value = "connect your network"
        } else {
            val call = CLRepository.follow(userId, token?.authToken, token?.refreshToken)
            call?.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    followFail.value = mApplication.getString(R.string.follow_fail)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        users?.let {
                            for (user in it) {
                                if (user.id == userId) {
                                    user.isFollowing = true
                                    CLRepository.updateUserDb(user)
                                    followSuccess.value =
                                        mApplication.getString(R.string.follow_success)
                                    retrieveFromDatabase()
                                    break
                                }
                            }
                        }
                    } else {
                        followFail.value = mApplication.getString(R.string.follow_fail)
                    }
                }
            })
        }
    }

    fun retrieveFromServer(pageId: Int) {
        val token = retrieveToken()
        if (!CLNetworkInterceptor().isInternetAvailable()) {
            serverFailure.value = "connect your network"
        } else {
            val response = CLRepository.getUsers(token?.authToken, pageId.toString())
            response?.enqueue(object : Callback<CLContacListUsers1> {
                override fun onFailure(call: Call<CLContacListUsers1>, t: Throwable) {
                    serverFailure.value = mApplication.getString(R.string.response_fails)
                }

                override fun onResponse(
                    call: Call<CLContacListUsers1>,
                    response: Response<CLContacListUsers1>
                ) {
                    if (response.code() == 200) {
                        serverSuccess.value = mApplication.getString(R.string.response_success)
                        val res = response.body()
                        CLRepository.insertList(res?.allUsers)
                        retrieveFromDatabase()
                    } else {
                        serverFailure.value = mApplication.getString(R.string.response_fails)
                    }
                }
            })
        }
    }

    fun filter(text: String?) {
        val temp = ArrayList<CLLoginResponseUser>()
        val token = Gson().fromJson(
            CLRepository.retrieveUserFromSharedPreference(),
            CLLoginResponseToken::class.java
        )
        val search = CLRepository.search(text, token)
        search?.enqueue(object : Callback<List<CLContacListUsers1?>> {
            override fun onResponse(
                call: Call<List<CLContacListUsers1?>>,
                response: Response<List<CLContacListUsers1?>>
            ) {
                if (response.isSuccessful) {
                    searchSuccess.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<CLContacListUsers1?>>, t: Throwable) {
                searchFail.value = "search is not working"
            }

        })

        CLRepository.getUserDetails(object : CLRepository.callback {
            override fun getList1(userList: List<CLLoginResponseUser>?) {
                users = userList
                Handler(Looper.getMainLooper()).post {
                    // things to do on the main thread
                    users?.forEach { d ->
                        val b = text?.let { d.firstName?.contains(it) }
                        val c = text?.let { d.lastName?.contains(it) }
                        if (b == true || c == true) {
                            temp.add(d)
                        }
                    }
                    searchResult.value = temp
                }
            }
        })
    }
}