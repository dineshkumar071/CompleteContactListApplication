package com.intern.internproject.enter_otp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseActivity
import com.intern.internproject.login.CLLoginActivity
import com.intern.internproject.respository.model.CLUSerEntity
import kotlinx.android.synthetic.main.cl_activity_enter_otp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class CLUserEnterOtpActivity : CLBaseActivity() {

    var otp: String? = null
    var otpViewModel: CLUserEnterOtpViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_activity_enter_otp)

        otpViewModel = ViewModelProvider(this).get(CLUserEnterOtpViewModel::class.java)
        btn_send_otp.setOnClickListener {
            otp = ev_enter_otp.text.toString()
            showProgressBar()
            otpViewModel?.accountRegistration(otp)
        }
        btn_resend_otp.setOnClickListener {
            val fm = supportFragmentManager
            val myFragment = CLUserEnterResendOtpFragment()
            myFragment.show(fm, "simple fragment")
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        otpViewModel?.registerLive?.observe(this, Observer {
            it?.enqueue(object : Callback<CLUSerEntity> {
                override fun onFailure(call: Call<CLUSerEntity>, t: Throwable) {
                    hideProgressBar()
                    Toast.makeText(
                        this@CLUserEnterOtpActivity,
                        "something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<CLUSerEntity>,
                    response: Response<CLUSerEntity>
                ) {
                    if (response.isSuccessful) {
                        hideProgressBar()
                        Toast.makeText(
                            this@CLUserEnterOtpActivity,
                            "Activation perfect",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@CLUserEnterOtpActivity, CLLoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        hideProgressBar()
                        Toast.makeText(
                            this@CLUserEnterOtpActivity,
                            "Activation imperfect",
                            Toast.LENGTH_SHORT
                        ).show()
                        ev_enter_otp.error = "invalid otp"
                    }
                }

            })

        })
    }

}