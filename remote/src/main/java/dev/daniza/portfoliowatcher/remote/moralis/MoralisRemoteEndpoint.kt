package dev.daniza.portfoliowatcher.remote.moralis

import dev.daniza.portfoliowatcher.model.moralis.MoralisTokenTrend
import retrofit2.http.GET
import retrofit2.http.Query

interface MoralisRemoteEndpoint {
    @GET
    fun getTrendingTokens(
        @Query("limit") limit: Int = 10,
        @Query("chain") chain: String = "eth",
    ): List<MoralisTokenTrend>
}