package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class SelfHostService(
    private val selfHostRemoteEndpoint: SelfHostRemoteEndpoint
) : SelfHostRemote {
    override suspend fun checkTokenUserSession(token: String): SelfHostResponse<UserSession> {
        val jsonToken = JSONObject().apply {
            put("token", token)
        }.toString()
        return selfHostRemoteEndpoint.checkTokenUserSession(jsonToken.toRequestBody())
    }

    override suspend fun getHomeDailySummaryData(
        symbols: List<String>
    ): SelfHostResponse<List<HomeDailySummaryModel>> {
        val jsonBody = JSONArray(symbols).toString()
        return selfHostRemoteEndpoint.getHomeDailySummaryData(jsonBody.toRequestBody())
    }

    override suspend fun getHomeDailyChartData(
        symbol: String,
        range: String
    ): SelfHostResponse<HomeDailyChartModel> {
        val body = JSONObject().apply {
            put("timeframe", range)
            put("symbol", symbol)
        }.toString()
        return selfHostRemoteEndpoint.getHomeDailyChartData(body.toRequestBody())
    }

    override suspend fun getHomeRecommendation(): HomeRecommendation {
        return selfHostRemoteEndpoint.getHomeRecommendation()
    }

    override suspend fun getMarketPopular(): SelfHostResponse<MarketPopularModel> {
        return selfHostRemoteEndpoint.getMarketPopularData()
    }
}