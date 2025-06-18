package dev.daniza.portfoliowatcher.remote.tokenmetrics

import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel

class TokenMetricsService(
    val tokenMetricsRemoteService: TokenMetricsRemoteEndpoint
) : TokenMetricsRemote {
    override suspend fun getSearchTokens(
        query: String,
        page: Int,
        pageSize: Int
    ): List<TokenSearchModel> {
        val response = tokenMetricsRemoteService.getSearchTokens(
            url = TOKEN_METRICS_SEARCH_URL,
            tokenName = query,
            page = page,
            limit = pageSize
        )
        return response.data
    }
}