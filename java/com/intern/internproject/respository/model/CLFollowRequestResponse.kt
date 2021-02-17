package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLFollowRequestResponse {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("gender")
    @Expose
    var gender: Any? = null

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null

    @SerializedName("issued_at")
    @Expose
    var issuedAt: Any? = null

    @SerializedName("dob")
    @Expose
    var dob: Any? = null

    @SerializedName("avatar")
    @Expose
    var avatar: Any? = null

    @SerializedName("address")
    @Expose
    var address: String? = null
}
