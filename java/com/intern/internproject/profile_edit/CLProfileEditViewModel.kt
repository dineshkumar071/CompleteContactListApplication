package com.intern.internproject.sign_up

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CLProfileEditViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    lateinit var user: CLUSerEntity
    var loginLiveData = MutableLiveData<Boolean>()
    var error = MutableLiveData<String?>()
    var success = MutableLiveData<String?>()
    var responseSuccess = MutableLiveData<String?>()
    var responseFail = MutableLiveData<String?>()
    var address = street1 + street2 + city + state + postCode

    fun validation() {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
                companyName
            )
            || TextUtils.isEmpty(street1) || TextUtils.isEmpty(street2) || TextUtils.isEmpty(state) || TextUtils.isEmpty(
                city
            ) || TextUtils.isEmpty(postCode)
        ) {
            error.value = mApplication.getString(R.string.all_field)
        } else {
            user = CLUSerEntity(
                firstName,
                lastName,
                companyName,
                eMail,
                phoneNumber,
                passWord,
                address,
                true,
                path
            )
            updateFromServerInRepository()
            //success.value="Sucess"
        }
    }

    private fun updateFromServerInRepository() {
        val users = CLEditUser()
        val user = CLEditFirstName(firstName, lastName)
        users.user = user
        val token = retrieveToken()
        val res = CLRepository.updateUser(token?.authToken, token?.refreshToken, users)
        res?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                responseFail.value = mApplication.getString(R.string.update_fail)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    responseSuccess.value = mApplication.getString(R.string.update_success)

                } else {
                    responseFail.value = mApplication.getString(R.string.update_fail)
                }
            }

        })
    }
}