package com.intern.internproject.description

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.example.CLLoginResponseUser
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLMessage
import com.intern.internproject.respository.model.CLUserDetails
import com.intern.internproject.services.CLNetworkInterceptor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CLDescriptionViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {

    var logInUser = MutableLiveData<String?>()
    var logoutResponseSuccess = MutableLiveData<String?>()
    var logoutResponseFailure = MutableLiveData<String?>()

    fun logoutRequest() {
        val token = retrieveToken()
        if(!CLNetworkInterceptor().isInternetAvailable()){
            logoutResponseFailure.value ="connect to your network"
        }else {
            val res = CLRepository.logoutUser(token?.authToken, token?.refreshToken)
            res?.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    logoutResponseFailure.value = mApplication.getString(R.string.logout_failure)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        logoutResponseSuccess.value =
                            mApplication.getString(R.string.logout_success)
                        myEvent.post(CLMessage("logout success"))
                    } else {
                        logoutResponseFailure.value =
                            mApplication.getString(R.string.logout_failure)
                    }
                }
            })
        }
    }

    fun retrieve() {
        CLRepository.getUserDetails(object : CLRepository.callback {
            override fun getList1(userList: List<CLLoginResponseUser>?) {
                users = userList
                // things to do on the main thread
                CLRepository.getUser(object : CLRepository.secondDatabaseCallBack {
                    override fun getUserDetail(user: CLUserDetails) {
                        Handler(Looper.getMainLooper()).post {
                            logInUser.value = user.Email
                            dbList.value = users
                        }
                    }
                })
            }
        })
    }

}