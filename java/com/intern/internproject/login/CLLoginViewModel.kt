package com.intern.internproject.login

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.CLLoginResponse
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLUserLogin
import com.intern.internproject.services.CLNetworkInterceptor
import com.intern.internproject.utility.CLUtilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CLLoginViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    var userName: String? = null
    var password: String? = null
    var empty = MutableLiveData<String>()
    var textError = MutableLiveData<String>()
    var success = MutableLiveData<String?>()
    var failure = MutableLiveData<String?>()
    var login: CLUserLogin? = null
    var singleUser: CLLoginResponse? = null

    fun validation() {

        if(!CLNetworkInterceptor().isInternetAvailable()){
            empty.value=mApplication.getString(R.string.connect_internet)
        }else{
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            empty.value = mApplication.getString(R.string.All_fields)
        } else {
            if (!CLUtilities.isValidEmail(userName ?: "")) {
                empty.value = mApplication.getString(R.string.invalid_user)
            } else if (!CLUtilities.isValidPassword(password ?: "")) {
                empty.value = mApplication.getString(R.string.invalis_password)
            } else {
                login = CLUserLogin(userName, password)
                val user = CLRepository.activateUser(login)
                user?.enqueue(object : Callback<CLLoginResponse> {
                    override fun onFailure(call: Call<CLLoginResponse>, t: Throwable) {
                        failure.value = mApplication.getString(R.string.went_wrong)
                    }

                    override fun onResponse(
                        call: Call<CLLoginResponse>,
                        response: Response<CLLoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val userResponse = response.body()
                            singleUser = userResponse
                            val token = singleUser?.tokens
                            val singleUser = singleUser?.user
                            CLRepository.saveUserInSharedPreference(token)
                            CLRepository.insert(singleUser)
                            CLRepository.insertDetails(userName, password)
                            success.value = mApplication.getString(R.string.login_success)
                            // Log.d("NEW", singleUser?.user.toString())
                        } else {
                            failure.value = mApplication.getString(R.string.invalid_login)

                        }

                    }

                })
            }
        }
        }
    }
}