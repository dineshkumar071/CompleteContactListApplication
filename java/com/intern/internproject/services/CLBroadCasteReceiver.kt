package com.intern.internproject.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.intern.internproject.common.CLNetworkStatus

class CLBroadCasteReceiver : BroadcastReceiver() {
    @SuppressLint("ShowToast")
    override fun onReceive(context: Context, intent: Intent?) {
       val networkStatus:String?=CLNetworkStatus.getConnectivityStatusString(context)
        Toast.makeText(context,networkStatus,Toast.LENGTH_SHORT).show()
    }
}