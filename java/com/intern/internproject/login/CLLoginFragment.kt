package com.intern.internproject.login

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseFragment
import com.intern.internproject.common.CLAlert
import com.intern.internproject.contact_list.CLContactListActivity
import com.intern.internproject.reset_password.CLForgotPasswordFragment
import com.intern.internproject.sign_up.CLSignUpFragment
import kotlinx.android.synthetic.main.cl_fragment_login.*

class CLLoginFragment : CLBaseFragment(), View.OnClickListener {
    private lateinit var clAlert: CLAlert
    private val loginViewModel: CLLoginViewModel by lazy {
        ViewModelProvider(this).get(
            CLLoginViewModel::class.java
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observerViewModal()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cl_fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                loginViewModel.userName = editable.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                loginViewModel.password = editable.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        btn_login.setOnClickListener(this)
        tv_forgotpassword.setOnClickListener(this)
        tv_signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                (activity as CLLoginActivity).showProgressBar()
                loginViewModel.validation()
            }
            R.id.tv_forgotpassword -> {
                val fm = (mContext as FragmentActivity).supportFragmentManager
                val myFragment = CLForgotPasswordFragment()
                myFragment.show(fm, "simple fragment")
            }
            R.id.tv_signup -> {
                showSignUpFragment()
            }
        }
    }

    private fun observerViewModal() {
        loginViewModel.empty.observe(viewLifecycleOwner, Observer {
            (activity as CLLoginActivity).hideProgressBar()
            val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                dialog?.dismiss()
            }
            clAlert = CLAlert.newInstance(getString(R.string.alert), it, positiveClickListener)
            clAlert.show(
                (mContext as FragmentActivity).supportFragmentManager,
                getString(R.string.fragement_dialogue)
            )
        })
        loginViewModel.textError.observe(viewLifecycleOwner, Observer {
            (activity as CLLoginActivity).hideProgressBar()
            it_username_input_text?.error = getString(R.string.inavlidEmail)
            it_password_inputtext?.error = getString(R.string.invalidPassword)
        })
        loginViewModel.success.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(mContext, CLContactListActivity::class.java)
            intent.putExtra("EMAIL_ID", loginViewModel.userName)
            startActivity(intent)
            (mContext as Activity).finish()
        })
        loginViewModel.failure.observe(viewLifecycleOwner, Observer {
            (activity as CLLoginActivity).hideProgressBar()
            Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            (activity as CLLoginActivity).hideProgressBar()
        })
    }

    private fun showSignUpFragment() {
        val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
        val fragment = CLSignUpFragment()
        transaction.replace(R.id.fl_frameHolder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

}