package com.intern.internproject.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId.getInstance
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.intern.internproject.login.CLLoginActivity
import com.intern.internproject.R
import com.intern.internproject.common.CLConstants.BODY
import com.intern.internproject.common.CLConstants.NOTIFICATION_CHANNEL_ID
import com.intern.internproject.common.CLConstants.TITLE
import kotlin.random.Random


class CLFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        var deviceToken: String? =null
        getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                 deviceToken = instanceIdResult.token
                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server
            }
        sendNewTokenToTheServer(deviceToken)
    }

    private fun sendNewTokenToTheServer(token: String?) {
        Log.d("token",token)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if(p0.data.isEmpty())
            generateNotification(p0.notification?.title.toString(), p0.notification?.body.toString())
        else
            showNotification(p0.data)
    }

    private fun generateNotification(title: String, body: String) {
        val intent = Intent("ACTION")
        intent.putExtra(TITLE,title)
        intent.putExtra(BODY,body)
        sendBroadcast(intent)
    }

    private fun showNotification(data: Map<String, String>) {

        val title = data["Title"].toString()
        val body = data["Body"].toString()
        val intent = Intent(this, CLLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(TITLE,title)
        intent.putExtra(BODY,body)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "demo"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationManager.createNotificationChannel(notificationChannel)

        }

        val notificationCompat = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
        notificationCompat.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setContentInfo("Info")

        notificationManager.notify(Random.nextInt(),notificationCompat.build())

    }
}
