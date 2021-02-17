package com.intern.internproject.description

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.CLLoginResponseUser
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseFragment
import com.intern.internproject.login.CLLoginActivity
import com.intern.internproject.profile_edit.CLProfileEditFragment
import kotlinx.android.synthetic.main.cl_fragment_cldescription.*
import kotlinx.android.synthetic.main.cl_toolbar_decription.*

class CLDescriptionFragment : CLBaseFragment() {
    private lateinit var descriptionViewModel: CLDescriptionViewModel
    var users = ArrayList<CLLoginResponseUser>()
    var email: String? = null
    private var logInEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        email = bundle?.getString("EMAIL")
        logInEmail = bundle?.getString("LOG_IN_EMAIL")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout.setOnClickListener {
            descriptionViewModel.logoutRequest()
        }
        btn_cancel_tool.setOnClickListener {
            (mContext as Activity).finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.cl_fragment_cldescription, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        descriptionViewModel =
            ViewModelProvider(this).get(CLDescriptionViewModel::class.java)
        descriptionViewModel.retrieve()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        (mContext as Activity).finish()
    }

    private fun showSignUpEditFragment() {
        val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
        val fragment = CLProfileEditFragment()
        transaction.replace(R.id.fl_frameHolder_edit, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun observeViewModel() {
        var logIn: String? = null
        descriptionViewModel.dbList.observe(viewLifecycleOwner, Observer { valve ->
            descriptionViewModel.logInUser.observe(viewLifecycleOwner, Observer {
                logIn = it
            })
            for (i in valve) {
                if (i.email == email) {
                    tv_name.text = i.firstName
                    tv_company_name.text = getString(R.string.company)
                    tv_phone_number.text = i.phoneNumber
                    tv_email_content.text = i.email
                    tv_address_content.text = i.address
                    if (i.email == logIn) {
                        Log.d("enable", "worked")
                        btn_logout.visibility = View.VISIBLE
                        btn_save_tool.visibility = View.VISIBLE
                        btn_save_tool.setOnClickListener {
                            showSignUpEditFragment()
                        }
                    }
                }
            }
        })
        descriptionViewModel.logoutResponseSuccess.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(mContext, CLLoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            (mContext as Activity).finish()
        })
        descriptionViewModel.logoutResponseFailure.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
        })
    }

    fun newInstance(email: String?): CLDescriptionFragment {
        val obj = CLDescriptionFragment()
        val bundle = Bundle()
        bundle.putString("EMAIL", email)
        obj.arguments = bundle
        return obj
    }
}