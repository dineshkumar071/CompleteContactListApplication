package com.example

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLLoginResponse {
    @SerializedName("Tokens")
    @Expose
    var tokens: CLLoginResponseToken? = null

    @SerializedName("User")
    @Expose
    var user: CLLoginResponseUser? = null
}