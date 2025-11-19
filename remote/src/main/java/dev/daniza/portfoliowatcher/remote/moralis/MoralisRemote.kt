package dev.daniza.portfoliowatcher.remote.moralis

import dev.daniza.portfoliowatcher.model.moralis.MoralisTokenTrend

interface MoralisRemote {
    suspend fun getTrendingTokens(
        limit: Int = 10,
    ): List<MoralisTokenTrend>
}