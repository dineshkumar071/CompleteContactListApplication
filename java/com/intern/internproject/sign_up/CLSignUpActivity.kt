package com.intern.internproject.sign_up

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseActivity
import com.intern.internproject.common.CLAlert
import com.intern.internproject.contact_list.CLContactListActivity
import com.intern.internproject.utility.CLUtilities
import kotlinx.android.synthetic.main.cl_fragment_signup.*
import kotlinx.android.synthetic.main.cl_toolbar_signup.*


class CLSignupActivity : CLBaseActivity() {
    private lateinit var signUpViewModel: CLSignUpViewModel
    var clAlert: CLAlert? =null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_fragment_signup)
        signUpViewModel = ViewModelProvider(this).get(CLSignUpViewModel::class.java)
        et_firstname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.firstName = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_lastname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.lastName = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_companyname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.companyName = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.eMail = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.phoneNumber=edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.passWord = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_confirmpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.confirmPassword = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_street1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.street1 = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_street2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.street2 = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.city = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_state.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.state = edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_postcode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.postCode=edit.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        btn_save_tool.setOnClickListener {
            signUpViewModel.validation()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        signUpViewModel.error.observe(this, Observer { error ->
            if (error!=null) {
                val onClickListener =
                    DialogInterface.OnClickListener { _, _ -> clAlert?.dismiss() }
                CLUtilities.showAlertDialogue(this as FragmentActivity, error, onClickListener)
            }
        })
        signUpViewModel.success.observe(this, Observer { success ->

                val onClickListener =
                    DialogInterface.OnClickListener { _, _ ->   nextActivity()}
            CLUtilities.showAlertDialogue(this as FragmentActivity,success, onClickListener)


        })
    }

    private fun nextActivity() {
        var contactListIntent = Intent(
            this,
            CLContactListActivity::class.java
        )
        startActivity(contactListIntent)
        finish()
    }
}

