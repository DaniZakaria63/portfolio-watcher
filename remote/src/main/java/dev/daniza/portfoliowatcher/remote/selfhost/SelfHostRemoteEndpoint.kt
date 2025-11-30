package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SelfHostRemoteEndpoint {

    @POST("/api/check-token")
    suspend fun checkTokenUserSession(
        @Body token: RequestBody
    ): SelfHostResponse<UserSession>

    @POST("/api/home-daily")
    suspend fun getHomeDailySummaryData(
        @Body body: RequestBody
    ) : SelfHostResponse<List<HomeDailySummaryModel>>
}