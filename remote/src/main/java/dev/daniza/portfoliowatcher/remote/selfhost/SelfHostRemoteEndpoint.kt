package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

const val SELFHOST_BASE_URL: String = "https://9yqol.wiremockapi.cloud/"

interface SelfHostRemoteEndpoint {
    @FormUrlEncoded
    @POST("/token/validate")
    suspend fun checkTokenUserSession(
        @Field("token") token: String
    ): SelfHostResponse<UserSession>
}