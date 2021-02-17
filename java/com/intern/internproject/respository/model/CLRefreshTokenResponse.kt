package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CLRefreshTokenResponse {
    @SerializedName("auth_token")
    @Expose
    var authToken: String? = null
}
