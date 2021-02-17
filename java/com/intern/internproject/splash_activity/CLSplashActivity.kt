package com.intern.internproject.splash_activity

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager.LayoutParams.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.intern.internproject.R
import com.intern.internproject.app_versioning.returnVersionNumber
import com.intern.internproject.base.CLBaseActivity
import com.intern.internproject.common.CLAlert
import com.intern.internproject.contact_list.CLContactListActivity
import com.intern.internproject.login.CLLoginActivity
import com.intern.internproject.respository.CLRepository

class CLSplashActivity : CLBaseActivity() {
    var splashViewModel: CLSplashViewModel? = null
    private lateinit var clAlert: CLAlert
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FcmTest","Token"+FirebaseInstanceId.getInstance().token)
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices")
        splashViewModel = ViewModelProvider(this).get(CLSplashViewModel::class.java)
        //get the version number
        val verName = returnVersionNumber()
        //send the api number for API hit
        splashViewModel?.saveVersionNameToServer(verName)
        //hiding the title bar of this activity.
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity in full screen.
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
        setContentView(R.layout.cl_activity_splash)
        //set splash timing.
        observeViewModel()
        if(savedInstanceState==null) {
            Handler().postDelayed({
                val check = CLRepository.retrieveUserFromSharedPreference()
                if (check == "") {
                    Log.d("VAL", "one")
                    startActivity(Intent(this, CLLoginActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, CLContactListActivity::class.java))
                    finish()
                }
            }, 3000)
        }
    }

    private fun observeViewModel() {
        splashViewModel?.responseSuccess?.observe(this, Observer {
            when (it?.updateStatus) {
                "force" -> {
                    val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store")
                            )
                        )
                    }
                    clAlert = CLAlert.newInstance(
                        "Alert",
                        "update the application",
                        positiveClickListener
                    )
                    clAlert.show(
                        this@CLSplashActivity.supportFragmentManager,
                        "fragment_confirm_dialog"
                    )
                }
                "update" -> {
                    val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                        dialog?.dismiss()
                    }
                    clAlert = CLAlert.newInstance(
                        "Alert",
                        "update the application",
                        positiveClickListener
                    )
                    clAlert.show(
                        this@CLSplashActivity.supportFragmentManager,
                        "fragment_confirm_dialog"
                    )
                }
                else -> {
                    Toast.makeText(this, it?.updateStatus, Toast.LENGTH_LONG).show()
                }
            }
        })
        splashViewModel?.responseFail?.observe(this, Observer { fail ->
            Toast.makeText(this, fail, Toast.LENGTH_SHORT).show()
        })
    }
}
