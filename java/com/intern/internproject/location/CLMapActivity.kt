package com.intern.internproject.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.intern.internproject.R
import kotlinx.android.synthetic.main.cl_activity_map.*

class CLMapActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener,
    GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var mapView: View
    private val addressChooserViewModel: CLMapViewModel by lazy {
        ViewModelProvider(this).get(
            CLMapViewModel::class.java
        )
    }


    companion object {
        const val DEFAULT: Float = 17.5F
        const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_activity_map)
        setSupportActionBar(tb_chooseAddress)
        tb_chooseAddress.title = getString(R.string.choose_address)
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapView = mapFragment.view as View
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        observeViewModel()
        btn_save_address.setOnClickListener(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0!!
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.setOnMapClickListener(this)
        googleMap?.setOnMapLongClickListener(this)

        googleMap?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker?) {
                val newLatLng = p0?.position
                addressChooserViewModel.showAddress(newLatLng!!)
            }

            override fun onMarkerDragStart(p0: Marker?) {}
            override fun onMarkerDrag(p0: Marker?) {}
        })

        if (mapView.findViewById<View?>("1".toInt()) != null) {
            val locationButton =
                (mapView.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 0, 40, 180)
        }

        val locator = LocationRequest()
        locator.interval = 1000
        locator.fastestInterval = 3000
        locator.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locator)
        val settingClient = LocationServices.getSettingsClient(this)
        val task = settingClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this) {
            getDeviceLocation()
        }
        task.addOnFailureListener(this) { e ->
            if (e is ResolvableApiException) {
                e.startResolutionForResult(this, REQUEST_CODE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            getDeviceLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful) {
                lastLocation = it.result!!
                val marker = MarkerOptions()
                marker.position(LatLng(lastLocation.latitude, lastLocation.longitude))
                val p0 = LatLng(lastLocation.latitude, lastLocation.longitude)
                addressChooserViewModel.showAddress(p0)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(p0, DEFAULT))
                googleMap?.addMarker(marker)?.isDraggable = true
            } else {
                Toast.makeText(this, "unable to get last location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        with(addressChooserViewModel) {
            addressMarked.observe(this@CLMapActivity, Observer {
                et_addressChooser.setText(it)
            })
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                btn_save_address.id -> {
                    val address = et_addressChooser.text.toString()
                    val intent = Intent()
                    intent.putExtra("Address", address)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    override fun onMapLongClick(p0: LatLng?) {
        val latLng = LatLng(p0!!.latitude, p0.longitude)
        addressChooserViewModel.showAddress(latLng)
    }

    override fun onMapClick(p0: LatLng?) {
        val latLng = LatLng(p0!!.latitude, p0.longitude)
        addressChooserViewModel.showAddress(latLng)
    }
}