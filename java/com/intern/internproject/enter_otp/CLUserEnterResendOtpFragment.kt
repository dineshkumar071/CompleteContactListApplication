package com.intern.internproject.enter_otp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.intern.internproject.R
import kotlinx.android.synthetic.main.cl_alertbox_resend_otp.*

class CLUserEnterResendOtpFragment:DialogFragment() {
    private lateinit var mContext:Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_cancelation.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cl_alertbox_resend_otp,container,false)
    }
}