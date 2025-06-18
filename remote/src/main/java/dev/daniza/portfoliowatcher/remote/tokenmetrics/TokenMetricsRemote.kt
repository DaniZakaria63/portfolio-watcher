package dev.daniza.portfoliowatcher.remote.tokenmetrics

import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel

interface TokenMetricsRemote {
    suspend fun getSearchTokens(
        query: String,
        page: Int = 1,
        pageSize: Int = 10,
    ): List<TokenSearchModel>
}