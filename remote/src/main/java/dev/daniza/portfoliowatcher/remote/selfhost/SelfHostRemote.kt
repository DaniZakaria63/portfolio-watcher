package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse

interface SelfHostRemote {
    suspend fun checkTokenUserSession(token: String) : SelfHostResponse<UserSession>

    suspend fun getHomeDailySummaryData(
        token: String,
        symbols: List<String>
    ) : SelfHostResponse<List<HomeDailySummaryModel>>
}