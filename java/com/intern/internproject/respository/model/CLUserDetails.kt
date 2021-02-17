package com.intern.internproject.respository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class CLUserDetails(
    @ColumnInfo(name = "EMAIL") @SerializedName("email") @Expose var Email: String?,
    @ColumnInfo(name = "PASSWORD") @SerializedName("password") @Expose var passWord: String?
) {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null
}