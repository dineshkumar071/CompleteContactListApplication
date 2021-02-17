package com.intern.internproject.respository.model

import com.example.CLLoginResponseToken
import com.example.CLLoginResponseUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLSharedPreference {
    @SerializedName("nameValuePairs")
    @Expose
    var hashMap = mutableMapOf<String?, CLLoginResponseToken?>()
}