package com.intern.internproject.sign_up

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseFragment
import com.intern.internproject.common.CLAlert
import com.intern.internproject.enter_otp.CLUserEnterOtpActivity
import com.intern.internproject.location.CLMapActivity
import com.intern.internproject.login.CLLoginActivity
import com.intern.internproject.services.CLNetworkInterceptor
import com.intern.internproject.utility.CLUtilities
import com.intern.internproject.web_view.CLWebViewActivity
import kotlinx.android.synthetic.main.cl_fragment_signup.*
import kotlinx.android.synthetic.main.cl_toolbar_signup.*
import java.util.*

class CLSignUpFragment :CLBaseFragment(),View.OnClickListener{

    private lateinit var signUpViewModel: CLSignUpViewModel
    private var isManualPermissionRequired = false
    private var isNavigateToMap = false
    private val isNetworkPresent: CLNetworkInterceptor by lazy { CLNetworkInterceptor() }
    private var hideAddress: Boolean = false

    companion object {
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1
        const val GOOGLE_MAP_PERMIT = 300
    }


    private lateinit var clAlert: CLAlert
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FragmentActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationPermission()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signUpViewModel = ViewModelProvider(this).get(CLSignUpViewModel::class.java)
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cl_fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_firstname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.firstName = edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_lastname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.lastName=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_companyname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.companyName=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.eMail=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.phoneNumber=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.passWord=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_confirmpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.confirmPassword=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_street1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.street1=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_street2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.street2=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_city.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.city=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_state.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.state=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        et_postcode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
                signUpViewModel.postCode=edit.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        btn_save_tool.setOnClickListener(this)
        btn_terms_conditions.setOnClickListener(this)
        tv_auto_update.setOnClickListener(this)
        tv_google_map.setOnClickListener(this)
        btn_cancel_tool.setOnClickListener(this)
    }


    private fun getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val getPermission = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(getPermission, REQUEST_CODE_LOCATION_PERMISSION)
            } else {
                if (isNavigateToMap) {
                    navigateToGoogleMap()
                } else {
                    getAddress()
                }
            }
        } else {
            if (isNavigateToMap) {
                navigateToGoogleMap()
            } else {
                getAddress()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isNavigateToMap) {
                        navigateToGoogleMap()
                    } else {
                        getAddress()
                    }
                } else if (isManualPermissionRequired) {
                    val fragmentManager =
                        (mContext as FragmentActivity).supportFragmentManager
                    val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                        dialog?.dismiss()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", mContext.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog?.dismiss()
                        Toast.makeText(mContext, "Denied", Toast.LENGTH_LONG).show()
                    }
                    val alertDialog = CLAlert.newInstance(
                        "Alert",
                        "To Perform the Action Permission must required, to give permission manually click \\'ok\\",
                        positiveClickListener
                    )
                    alertDialog.show(fragmentManager, "alertDialog")
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun getAddress() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        LocationServices.getFusedLocationProviderClient(CLUtilities.mContext)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(CLUtilities.mContext)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        locationResult.locations.size - 1
                        val geoCoder = Geocoder(CLUtilities.mContext, Locale.getDefault())
                        val addresses: List<Address> = geoCoder.getFromLocation(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude,
                            1
                        )
                        val value = addresses[0].getAddressLine(0)
                        signUpViewModel.setAddressInFormat(value)
                    }
                }
            }, Looper.getMainLooper())
    }

    private fun navigateToGoogleMap() {
        isNavigateToMap = false
        if (isNetworkPresent.isInternetAvailable()) {
            startActivityForResult(
                Intent(mContext, CLMapActivity::class.java),
                GOOGLE_MAP_PERMIT
            )
        } else {
            Toast.makeText(mContext, "No Internet", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_MAP_PERMIT && resultCode == Activity.RESULT_OK) {
            val result: String? = data?.getStringExtra("Address")
            result?.let { signUpViewModel.setAddressInFormatFromGoogle(it) }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save_tool -> {
                signUpViewModel.validation()
            }
            R.id.btn_terms_conditions -> {
                val intent = Intent(mContext, CLWebViewActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_auto_update -> {
                hideAddress = true
                getLocationPermission()
                isManualPermissionRequired = true
            }
            R.id.tv_google_map -> {
                isNavigateToMap = true
                getLocationPermission()
            }
            R.id.btn_cancel_tool -> {
                closeFragment()
            }
        }
    }

    private fun closeFragment() {
        val fragmentManager = (mContext as FragmentActivity).supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            val entry: FragmentManager.BackStackEntry = fragmentManager.getBackStackEntryAt(0)
            fragmentManager.popBackStack(entry.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
    private fun observeViewModel() {
        signUpViewModel.popUp.observe(viewLifecycleOwner, Observer {
            (activity as CLLoginActivity).showProgressBar()
        })
        signUpViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            val positiveClickListener = DialogInterface.OnClickListener { dialog, _ ->
                dialog?.dismiss()
            }
            clAlert = CLAlert.newInstance("Alert", error, positiveClickListener)
            clAlert.show(
                (mContext as FragmentActivity).supportFragmentManager,
                "fragment_confirm_dialog"
            )
        })
        signUpViewModel.postLive.observe(viewLifecycleOwner, Observer { postLive ->
            (activity as CLLoginActivity).hideProgressBar()
            Toast.makeText(mContext, postLive, Toast.LENGTH_SHORT).show()
        })
        signUpViewModel.signUp.observe(viewLifecycleOwner, Observer { signup ->
            (activity as CLLoginActivity).hideProgressBar()
            Toast.makeText(mContext, signup, Toast.LENGTH_SHORT).show()
            val intent = Intent(mContext, CLUserEnterOtpActivity::class.java)
            startActivity(intent)
            (mContext as FragmentActivity).finish()
        })
        signUpViewModel.locationAddress.observe(viewLifecycleOwner, Observer {
            if (hideAddress) {
                et_street1.setText(it)
                hideAddress = false
            }
        })
        signUpViewModel.googleAddress.observe(viewLifecycleOwner, Observer {
            et_street2.setText(it)
        })
    }

}