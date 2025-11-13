package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import retrofit2.http.GET

const val SELFHOST_BASE_URL: String = ""

interface SelfHostRemoteEndpoint {
    @GET("")
    fun checkTokenUserSession(
        token: String
    ): SelfHostResponse
}