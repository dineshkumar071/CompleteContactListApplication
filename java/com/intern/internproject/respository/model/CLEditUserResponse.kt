package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLEditUserResponse {
    @SerializedName("Message")
    @Expose
    var message: String? = null
}