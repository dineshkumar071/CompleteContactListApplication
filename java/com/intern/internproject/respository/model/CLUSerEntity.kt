package com.intern.internproject.respository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class CLUSerEntity(
    @ColumnInfo(name = "FIRST_NAME")
    @SerializedName("first_name")
    @Expose var firstName: String?,
    @ColumnInfo(name = "LAST_NAME") @SerializedName("last_name") @Expose var lastName: String?,
    @ColumnInfo(name = "COMPANY_NAME") var companyName: String?,
    @ColumnInfo(name = "EMAIL") @SerializedName("email") @Expose var Email: String?,
    @ColumnInfo(name = "PHONE_NUMBER") @SerializedName("phone_number") @Expose var phoneNumber: String?,
    @ColumnInfo(name = "PASSWORD") @SerializedName("password") @Expose var passWord: String?,
    @ColumnInfo(name = "ADDRESS") @SerializedName("address") @Expose var address: String?,
    @ColumnInfo(name = "LOG_IN") var logIn: Boolean,
    @ColumnInfo(name = "IMAGE_PATH") var imagepath: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}