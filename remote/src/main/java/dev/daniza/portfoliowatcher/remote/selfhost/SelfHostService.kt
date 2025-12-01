package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
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
        token: String,
        symbols: List<String>
    ): SelfHostResponse<List<HomeDailySummaryModel>> {
        val jsonBody = JSONObject().apply {
            put("token", token)
            put("symbol", JSONArray(symbols))
        }.toString()
        return selfHostRemoteEndpoint.getHomeDailySummaryData(jsonBody.toRequestBody())
    }
}