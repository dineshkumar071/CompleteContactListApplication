package com.intern.internproject.location

import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import java.util.*

class CLMapViewModel(mApplication: Application) : AndroidViewModel(mApplication) {

    private val context = getApplication<Application>().applicationContext
    val addressMarked = MutableLiveData<String>()

    fun showAddress(p0: LatLng) {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geoCoder.getFromLocation(p0.latitude, p0.longitude, 1)
        val address = addresses[0].getAddressLine(0)
        addressMarked.value = address
    }

}