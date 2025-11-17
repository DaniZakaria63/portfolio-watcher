package dev.daniza.portfoliowatcher.remote.parser

data class SelfHostResponse<T: Any>(
    val status: Int = 404,
    val message: String = "",
    val data: T? = null
)
