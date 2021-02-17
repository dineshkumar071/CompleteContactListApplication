package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLAppVersioningFullResponse {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("data")
    @Expose
    var data: CLAppVersioningResponse? = null
}