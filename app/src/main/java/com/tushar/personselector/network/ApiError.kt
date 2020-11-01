package com.tushar.personselector.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class wraps the error response of Persons response
 */
data class ApiError(
    @SerializedName("status_code")
    @Expose
    val errorCode: String? = "",
    @SerializedName("status_message")
    @Expose
    val errorMessage: String? = ""
)