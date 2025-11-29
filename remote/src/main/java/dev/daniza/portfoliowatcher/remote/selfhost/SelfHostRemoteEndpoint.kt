package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

const val SELFHOST_BASE_URL: String = "http://auth.walawe.fun"

interface SelfHostRemoteEndpoint {

    @POST("/api/check-token")
    suspend fun checkTokenUserSession(
        @Body token: RequestBody
    ): SelfHostResponse<UserSession>
}