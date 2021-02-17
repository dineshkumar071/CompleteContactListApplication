package com.intern.internproject.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CLAlert : DialogFragment() {
    private lateinit var title: String
    lateinit var message: String
    private var positiveButtonClickListener: DialogInterface.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(ARGS_TITLE).toString()
        message = arguments?.getString(ARGS_MESSAGE).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.let {
            it.setTitle(title)
            it.setMessage(message)
            it.setPositiveButton(
                "Ok", positiveButtonClickListener
            )
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    companion object {
        private const val ARGS_TITLE = " Title"
        private const val ARGS_MESSAGE = "Message"

        fun newInstance(
            title: String?,
            message: String?,
            positive: DialogInterface.OnClickListener?
        ): CLAlert = CLAlert().apply {
            arguments = Bundle().apply {
                putString(ARGS_TITLE, title)
                putString(ARGS_MESSAGE, message)
                positiveButtonClickListener = positive
            }
        }
    }
}