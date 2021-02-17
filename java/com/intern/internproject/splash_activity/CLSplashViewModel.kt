package com.intern.internproject.splash_activity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLAppVersioningFullResponse
import com.intern.internproject.respository.model.CLAppVersioningResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CLSplashViewModel (mApplication: Application) : CLBaseViewModel(mApplication){
    var responseSuccess=MutableLiveData<CLAppVersioningResponse?>()
    var responseFail=MutableLiveData<String?>()
    fun saveVersionNameToServer(versionName:String?)
    {
        val call =CLRepository.updateVersionName(versionName)
        call?.enqueue(object : Callback<CLAppVersioningFullResponse> {
            override fun onFailure(call: Call<CLAppVersioningFullResponse>, t: Throwable) {
                responseFail.value=mApplication.getString(R.string.version_not_updated)
            }

            override fun onResponse(call: Call<CLAppVersioningFullResponse>, response: Response<CLAppVersioningFullResponse>) {
                val res=response.body()
                if (response.isSuccessful){
                    responseSuccess.value=res?.data
                }else{
                    responseFail.value=mApplication.getString(R.string.version_not_updated)
                }
            }
        })
    }
}