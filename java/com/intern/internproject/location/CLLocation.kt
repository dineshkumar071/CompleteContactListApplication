package com.intern.internproject.location

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.text.TextUtils
import com.intern.internproject.common.CLConstants
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CLLocation : IntentService("CLLocation") {
    private var resultReceiver: ResultReceiver? = null

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            var errorMessage = ""
            resultReceiver = intent.getParcelableExtra(CLConstants.RECEIVER)
            val location: Location = intent.getParcelableExtra<Location>(CLConstants.LOCATION_DATA_EXTRA)
                ?: return
            val geocoder = Geocoder(this, Locale.getDefault())
             var addresses: List<Address>? = null
            try {
                addresses = location.latitude.let { geocoder.getFromLocation(it,
                    location.longitude, 1) }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
            if (addresses == null || addresses.isEmpty()) {
                delieverResultToReceiver(CLConstants.FAILURE_RESULT, errorMessage)
            } else {
                val address = addresses[0]
                val addressFragments =
                    ArrayList<String?>()
                for (i in 0..address.maxAddressLineIndex) {
                    addressFragments.add(address.getAddressLine(i))
                }
                delieverResultToReceiver(
                    CLConstants.SUCCESS_RESULT,
                    TextUtils.join(
                        Objects.requireNonNull(
                            System.getProperty("line.separator")
                        ), addressFragments
                    )
                )
            }
        }
    }

    private fun delieverResultToReceiver(
        resultCode: Int,
        addressMessage: String?
    ) {
        val bundle = Bundle()
        bundle.putString(CLConstants.RESULT_DATA_KEY, addressMessage)
        resultReceiver?.send(resultCode, bundle)
    }
}