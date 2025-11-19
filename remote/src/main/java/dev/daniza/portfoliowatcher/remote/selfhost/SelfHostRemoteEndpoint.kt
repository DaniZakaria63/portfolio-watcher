package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import retrofit2.http.Body
import retrofit2.http.POST

const val SELFHOST_BASE_URL: String = "https://9yqol.wiremockapi.cloud"

interface SelfHostRemoteEndpoint {
    @POST("/token/validate")
    suspend fun checkTokenUserSession(
        @Body token: String
    ): SelfHostResponse<UserSession>
}