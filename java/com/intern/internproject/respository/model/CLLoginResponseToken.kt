package com.example

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLLoginResponseToken {
    @SerializedName("Auth_token")
    @Expose
    var authToken: String? = null

    @SerializedName("Refresh_token")
    @Expose
    var refreshToken: String? = null

}
