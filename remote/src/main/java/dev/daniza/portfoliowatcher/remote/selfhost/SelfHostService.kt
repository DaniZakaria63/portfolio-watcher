package dev.daniza.portfoliowatcher.remote.selfhost

import com.google.gson.Gson
import dev.daniza.portfoliowatcher.model.parser.fromString
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
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
        token: String,
        symbol: String,
        range: String
    ): SelfHostResponse<HomeDailyChartModel> {
        val body = JSONObject().apply {
            put("token", token)
            put("symbol", JSONArray().apply {
                put(0, symbol) }
            )
            put("function", range)
        }.toString()
        return selfHostRemoteEndpoint.getHomeDailyChartData(body.toRequestBody())
    }

    override suspend fun getHomeRecommendation(): HomeRecommendation {
        return selfHostRemoteEndpoint.getHomeRecommendation()
    }
}