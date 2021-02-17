package com.intern.internproject.respository.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLUserActivation(
    @ColumnInfo(name = "EMAIL") @SerializedName("email") @Expose var Email: String?,
    @ColumnInfo(name = "PASSWORD") @SerializedName("password") @Expose var passWord: String?
) {
}