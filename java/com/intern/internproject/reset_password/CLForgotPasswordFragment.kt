package com.intern.internproject.reset_password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.intern.internproject.R
import com.intern.internproject.respository.CLRepository
import kotlinx.android.synthetic.main.cl_alertbox_forget_password.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CLForgotPasswordFragment : DialogFragment() {
    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_forgot_cancel.setOnClickListener {
            dismiss()
        }
        btn_send_otp.setOnClickListener {
            if (et_enter_email.text.toString() == "") {
                Toast.makeText(mContext, getString(R.string.enter_the_email), Toast.LENGTH_LONG)
                    .show()
            } else {
                dismiss()
                CLRepository.emailVerification(et_enter_email.text.toString()).let {
                    it?.enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(
                                mContext,
                                getString(R.string.email_wrong),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    mContext,
                                    getString(R.string.email_success),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    mContext,
                                    getString(R.string.email_fail),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    })
                }
                val fm = (mContext as FragmentActivity).supportFragmentManager
                val myFragment = CLOtpFragment()
                myFragment.show(fm, "simple fragment")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cl_alertbox_forget_password, container, false)
    }
}