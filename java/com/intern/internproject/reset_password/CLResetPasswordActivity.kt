package com.intern.internproject.reset_password

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseActivity
import com.intern.internproject.common.CLAlert
import kotlinx.android.synthetic.main.cl_activity_reset_password.*
import kotlinx.android.synthetic.main.cl_toolbar_reset_password.*

class CLResetPasswordActivity : CLBaseActivity() {

    private lateinit var resetPasswordViewModel: CLResetPasswordViewModel
    var otp: String? = null
    lateinit var resetPasswordAlert: CLAlert
    private val alertFragment = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_activity_reset_password)
        resetPasswordViewModel = ViewModelProvider(this).get(CLResetPasswordViewModel::class.java)
        otp = intent.getStringExtra(getString(R.string.OTP))
        resetPasswordViewModel.oneTimePassword = otp
        et_new_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                resetPasswordViewModel.newPassword = edit.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        et_confirm_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                resetPasswordViewModel.confirmPassword = edit.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        btn_done.setOnClickListener {
            showProgressBar()
            resetPasswordViewModel.validation()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        resetPasswordViewModel.stringEmpty.observe(this, Observer {
            hideProgressBar()
            val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                dialog?.dismiss()
            }
            resetPasswordAlert =
                CLAlert.newInstance(getString(R.string.alert), it, positiveClickListener)
            resetPasswordAlert.show(alertFragment, "fragment_confirm_dialog")
        })
        resetPasswordViewModel.notEqual.observe(this, Observer {
            hideProgressBar()
            val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                dialog?.dismiss()
            }
            resetPasswordAlert =
                CLAlert.newInstance(getString(R.string.alert), it, positiveClickListener)
            resetPasswordAlert.show(alertFragment, "fragment_confirm_dialog")
        })
        resetPasswordViewModel.responseSuccess.observe(this, Observer {
            hideProgressBar()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        resetPasswordViewModel.responseFail.observe(this, Observer {
            hideProgressBar()
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
}
