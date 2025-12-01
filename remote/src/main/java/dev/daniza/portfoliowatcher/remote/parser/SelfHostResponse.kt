package dev.daniza.portfoliowatcher.remote.parser

import com.google.gson.annotations.SerializedName

data class SelfHostResponse<T: Any>(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("data") val data: T? = null,
    @SerializedName("message") val message: String = ""
)
