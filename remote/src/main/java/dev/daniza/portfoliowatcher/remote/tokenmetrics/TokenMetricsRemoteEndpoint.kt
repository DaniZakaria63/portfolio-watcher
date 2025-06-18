package dev.daniza.portfoliowatcher.remote.tokenmetrics

import dev.daniza.portfoliowatcher.remote.BuildConfig
import dev.daniza.portfoliowatcher.remote.parser.TokenSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

const val TOKEN_METRICS_SEARCH_URL = BuildConfig.API_URL_TOKENMETRICS

interface TokenMetricsRemoteEndpoint {
    @GET
    suspend fun getSearchTokens(
        @Url url: String,
        @Query("token_name") tokenName: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
    ): TokenSearchResponse
}