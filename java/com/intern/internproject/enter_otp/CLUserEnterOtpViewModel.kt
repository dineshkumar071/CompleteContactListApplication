package com.intern.internproject.enter_otp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLUSerEntity
import retrofit2.Call

class CLUserEnterOtpViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    var registerLive = MutableLiveData<Call<CLUSerEntity>?>()
    fun accountRegistration(otp: String?) {
        registerLive.value = CLRepository.registerUser(otp)
    }
}