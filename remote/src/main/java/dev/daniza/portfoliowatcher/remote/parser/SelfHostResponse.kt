package dev.daniza.portfoliowatcher.remote.parser

data class SelfHostResponse(
    val status: Int = 404,
    val message: String = "",
    val data: Any? = null
)
