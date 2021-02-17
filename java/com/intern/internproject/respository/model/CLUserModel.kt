package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLUserModel {
    @SerializedName("user")
    @Expose
    var user: CLUSerEntity? = null
}