package com.intern.internproject.reset_password

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CLResetPasswordViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    var newPassword: String? = null
    var confirmPassword: String? = null
    var oneTimePassword: String? = null
    var stringEmpty = MutableLiveData<String?>()
    var notEqual = MutableLiveData<String?>()
    var responseSuccess = MutableLiveData<String?>()
    var responseFail = MutableLiveData<String?>()

    fun validation() {
        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            stringEmpty.value = mApplication.getString(R.string.enter_field)
        } else {
            if (newPassword == confirmPassword) {
                val resetResponse = CLRepository.resetPassword(oneTimePassword, newPassword)
                resetResponse?.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        responseFail.value = mApplication.getString(R.string.update_fail)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            responseSuccess.value = mApplication.getString(R.string.update_success)

                        } else {
                            responseFail.value = mApplication.getString(R.string.update_fail)
                        }
                    }
                })
            } else {
                notEqual.value = mApplication.getString(R.string.both_are_not_same)
            }
        }
    }
}