package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse

interface SelfHostRemote {
    suspend fun checkTokenUserSession(token: String): SelfHostResponse<UserSession>

    suspend fun getHomeDailySummaryData(
        symbols: List<String>
    ): SelfHostResponse<List<HomeDailySummaryModel>>

    suspend fun getHomeDailyChartData(
        symbol: String,
        range: String
    ): SelfHostResponse<HomeDailyChartModel>

    suspend fun getHomeRecommendation(isUseChart: Boolean): SelfHostResponse<HomeRecommendation>

    suspend fun getMarketPopular(): SelfHostResponse<MarketPopularModel>

    suspend fun getSearchStock(query: String): SelfHostResponse<List<MarketPopularModel.SmallQuote>>
}