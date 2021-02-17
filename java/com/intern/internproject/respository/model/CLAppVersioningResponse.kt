package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLAppVersioningResponse {
    @SerializedName("update_status")
    @Expose
    var updateStatus: String? = null
}