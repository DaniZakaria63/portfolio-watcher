package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SelfHostRemoteEndpoint {

    @POST("/auth/validate-token")
    suspend fun checkTokenUserSession(
        @Body token: RequestBody
    ): SelfHostResponse<UserSession>

    @POST("/finance/intraday/summary")
    suspend fun getHomeDailySummaryData(
        @Body body: RequestBody
    ) : SelfHostResponse<List<HomeDailySummaryModel>>

    @POST("/finance/intraday/chart")
    suspend fun getHomeDailyChartData(
        @Body body: RequestBody
    ) : SelfHostResponse<HomeDailyChartModel>

    @GET("/finance/market/popular")
    suspend fun getMarketPopularData() : SelfHostResponse<MarketPopularModel>

    @GET("/finance/recommendation")
    suspend fun getHomeRecommendation(
        @Query("include_chart") isUseChart: Boolean
    ) : SelfHostResponse<HomeRecommendation>
}