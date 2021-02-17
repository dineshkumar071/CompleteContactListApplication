package com.intern.internproject.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class CLNetworkStatus {
    companion object {
        private var TYPE_WIFI = 1
        private var TYPE_MOBILE = 2
        private var TYPE_NOT_CONNECTED = 0


        private fun getConnectivityStatus(context: Context): Int {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            val applicationContext = CLApplication.instance.applicationContext

            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (null != activeNetwork) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val network = connectivityManager.activeNetwork
                    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return TYPE_WIFI
                    }

                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return TYPE_MOBILE
                    }
                } else {

                    return TYPE_MOBILE
                }
            }
            return TYPE_NOT_CONNECTED
        }

        fun getConnectivityStatusString(context: Context): String? {
            val conn = getConnectivityStatus(context)
            var status: String? = null
            when (conn) {
                TYPE_WIFI -> {
                    status = "Wifi is detected"
                }
                TYPE_MOBILE -> {
                    status = "Mobile data connection is detected"
                }
                TYPE_NOT_CONNECTED -> {
                    status = "Not connected to Internet connection"
                }
            }
            return status
        }
    }
}