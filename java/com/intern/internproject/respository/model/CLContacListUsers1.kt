package com.intern.internproject.respository.model

import com.example.CLLoginResponseUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLContacListUsers1 {
    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("all_users")
    @Expose
    var allUsers: List<CLLoginResponseUser>? = null

}