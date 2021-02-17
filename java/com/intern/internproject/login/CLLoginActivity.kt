package com.intern.internproject.login

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseActivity
import com.intern.internproject.common.CLConstants.BODY
import com.intern.internproject.common.CLConstants.TITLE
import com.intern.internproject.services.CLBroadCasteReceiver

@Suppress("DEPRECATION")
class CLLoginActivity : CLBaseActivity() {
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_activity_login_signup)
        if (savedInstanceState == null) {
            showLoginFragment()
        }
        if (intent.extras != null) {
            val title = intent.extras!!.getString(TITLE)
            val body = intent.extras!!.getString(BODY)
            if (title != null && body != null) {
                val alert = AlertDialog.Builder(this)
                alert.setTitle(title)
                alert.setMessage(body)
                alert.show()
            }
        }
        val notify = CLReceiver()
        val filters = IntentFilter("ACTION")
        registerReceiver(notify, filters)
    }

    private fun showLoginFragment() {
        val transaction = manager.beginTransaction()
        val fragment = CLLoginFragment()
        transaction.replace(R.id.fl_frameHolder, fragment)
        transaction.commit()
    }

    private val network = CLBroadCasteReceiver()

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
        this.registerReceiver(network, filter)
    }

    class CLReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action?.equals("ACTION")!!) {
                    val title = intent.getStringExtra("title")
                    val body = intent.getStringExtra("body")
                    val alert = AlertDialog.Builder(context!!)
                    alert.setTitle(title)
                    alert.setMessage(body)
                    alert.show()
                }
            }
        }
    }

}
