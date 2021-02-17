package com.intern.internproject.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.intern.internproject.common.CLApplication.Companion.instance
import com.intern.internproject.respository.model.CLNoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class CLNetworkInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw CLNoInternetException(BAD_NETWORK)
        return chain.proceed(chain.request())
    }

    fun isInternetAvailable(): Boolean {
        val applicationContext = instance.applicationContext

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }

    }

    companion object {
        const val BAD_NETWORK = "check internet connection"
    }
}