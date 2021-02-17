package com.intern.internproject.utility

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.intern.internproject.common.CLAlert
import com.intern.internproject.common.CLApplication.Companion.instance
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLUSerEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.util.regex.Matcher
import java.util.regex.Pattern


object CLUtilities {
    var mContext=instance
    var longitude: Double? = null
    var latitude: Double? = null


    lateinit var alert: CLAlert
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        var validNum = 1
        var validCaps = 1
        var validSpcl = 1
        var exp: String
        var pattern: Pattern
        var matcher: Matcher
        if (password.length > 6) {
            exp = ".*[A-Z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(password)
            if (matcher.matches()) {
                validNum = 0
            }
            exp = ".*[0-9].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(password)
            if (matcher.matches()) {
                validCaps = 0
            }
            exp = ".*[!@#\$%^&*].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(password)
            if (matcher.matches()) {
                validSpcl = 0
            }
            return (validNum == 0) && (validCaps == 0) && (validSpcl == 0)
        } else {
            return false
        }
    }
    val currentLocation: Unit
        @SuppressLint("MissingPermission")
        get() {
            val locationRequest = LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 3000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            LocationServices.getFusedLocationProviderClient(mContext)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(mContext)
                            .removeLocationUpdates(this)
                        if (locationResult != null && locationResult.locations.size > 0) {
                            val latestLocation: Int = locationResult.locations.size - 1
                            latitude = locationResult.locations[latestLocation].latitude
                            longitude = locationResult.locations[latestLocation].longitude
                            val location = Location("providerNA")
                            location.latitude = latitude as Double
                            location.longitude = longitude as Double
                            //fetchAddressFromLatLong(location)
                        }
                    }
                }, Looper.getMainLooper())
        }


    fun showAlertDialogue(
        fragment: FragmentActivity,
        success: String?,
        onClickListener: DialogInterface.OnClickListener
    ) {
        alert =
            CLAlert.newInstance("ALERT", success, onClickListener)
        alert.show(fragment.supportFragmentManager, "fragment_confirm_dialog")
    }

    fun getBitmap(user: CLUSerEntity): Bitmap? {
        var bitmap: Bitmap? = null
        val f = File(user.imagepath)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
        return bitmap
    }

}