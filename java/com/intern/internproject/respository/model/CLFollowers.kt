package com.intern.internproject.respository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class CLFollowers(
    @SerializedName("address") @Expose var address: String? = null,
    @SerializedName("avatar") @Expose var avatar: String? = null,
    @SerializedName("dob") @Expose var dob: String? = null,
    @SerializedName("phone_number") @Expose var phoneNumber: String? = null,
    @SerializedName("gender") @Expose var gender: String? = null,
    @SerializedName("last_name") @Expose var lastName: String? = null,
    @SerializedName("first_name") @Expose var firstName: String? = null,
    @SerializedName("email") @Expose var email: String? = null
) {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null
    var isFollowing: Boolean = false
}