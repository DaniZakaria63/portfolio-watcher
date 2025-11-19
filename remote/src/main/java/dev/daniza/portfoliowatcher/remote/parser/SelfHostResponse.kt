package dev.daniza.portfoliowatcher.remote.parser

import com.google.gson.annotations.SerializedName

data class SelfHostResponse<T: Any>(
    @SerializedName("status") val status: Int = 404,
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: T? = null
)
