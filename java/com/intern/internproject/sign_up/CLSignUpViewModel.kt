package com.intern.internproject.sign_up

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.intern.internproject.R
import com.intern.internproject.respository.model.CLUSerEntity
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.services.CLNetworkInterceptor
import com.intern.internproject.utility.CLUtilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CLSignUpViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    lateinit var user: CLUSerEntity
    var confirmPassword: String? = null
    var error = MutableLiveData<String?>()
    var success = MutableLiveData<String?>()
    var postLive=MutableLiveData<String?>()
    var signUp=MutableLiveData<String?>()
    var popUp = MutableLiveData<String?>()
    var address:String?=null
    var locationAddress = MutableLiveData<String?>()
    var googleAddress = MutableLiveData<String>()

    private fun savedInDatabase(obj: CLUSerEntity?) {
        val post=CLRepository.insertUser(obj)
        post?.enqueue(object : Callback<CLUSerEntity> {
            override fun onFailure(call: Call<CLUSerEntity>, t: Throwable) {
               postLive.value=mApplication.getString(R.string.went_wrong)
            }

            override fun onResponse(call: Call<CLUSerEntity>, response: Response<CLUSerEntity>) {
                when {
                    response.isSuccessful -> {
                        signUp.value=mApplication.getString(R.string.signup_success)
                    }
                    response.code()==401 -> {
                        postLive.value=mApplication.getString(R.string.email_exist)
                    }
                    else -> postLive.value=mApplication.getString(R.string.signup_unsuccess)
                }
            }
        })
    }

    fun validation() {
        if(!CLNetworkInterceptor().isInternetAvailable()){
            error.value=mApplication.getString(R.string.connect_internet)
        }else {
            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                    companyName
                ) || TextUtils.isEmpty(eMail)
                || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(passWord) || TextUtils.isEmpty(
                    confirmPassword
                )
                || TextUtils.isEmpty(street1) || TextUtils.isEmpty(street2) || TextUtils.isEmpty(
                    state
                ) || TextUtils.isEmpty(
                    city
                ) || TextUtils.isEmpty(postCode)
            ) {
                error.value = mApplication.getString(R.string.all_field)
            } else {
                val isEmailValid = CLUtilities.isValidEmail(eMail ?: "")
                val isPasswordValid = CLUtilities.isValidPassword(passWord ?: "")
                if (!isEmailValid) {
                    error.value = mApplication.getString(R.string.email_invalid)
                } else if (!isPasswordValid) {
                    error.value = mApplication.getString(R.string.password_invalid)
                } else if (passWord != confirmPassword) {
                    error.value = mApplication.getString(R.string.pass)
                } else {
                    address = "$street1,$street2,$city,$state,$postCode"
                    user =
                        CLUSerEntity(
                            firstName,
                            lastName,
                            companyName,
                            eMail,
                            phoneNumber,
                            passWord,
                            address,
                            true,
                            null
                        )
                    popUp.value = "okay"
                    savedInDatabase(user)
                }
            }
        }
    }
    fun setAddressInFormat(value: String) {
        var addressFormat = ""
        val result = value.split(", ")
        for (i in result.indices) {
            addressFormat = if (i == 0)
                result[i]
            else
                addressFormat + ", \n" + result[i]
        }
        locationAddress.value = addressFormat
    }
    fun setAddressInFormatFromGoogle(value: String) {
        var addressFormat = ""
        val result = value.split(", ")
        for (i in result.indices) {
            addressFormat = if (i == 0)
                result[i]
            else
                addressFormat + ", \n" + result[i]
        }
        googleAddress.value = addressFormat
    }
}