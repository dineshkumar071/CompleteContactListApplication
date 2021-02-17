package com.intern.internproject.respository.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLUserLogin(
    @ColumnInfo(name = "EMAIL") @SerializedName("email") @Expose var email: String?,
    @ColumnInfo(name = "PASSWORD") @SerializedName("password") @Expose var passWord: String?
) {
}