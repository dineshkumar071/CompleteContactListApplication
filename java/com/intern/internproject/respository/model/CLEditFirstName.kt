package com.intern.internproject.respository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CLEditFirstName(
    @SerializedName("first_name") @Expose var firstName: String? = null,
    @SerializedName("last_name") @Expose var lastName: String? = null
) {}