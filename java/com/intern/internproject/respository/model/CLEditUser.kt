package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLEditUser {
    @SerializedName("user") @Expose
    var user :CLEditFirstName? = null
}