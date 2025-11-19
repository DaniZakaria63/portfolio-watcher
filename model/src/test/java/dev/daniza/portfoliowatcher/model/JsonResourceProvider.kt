package dev.daniza.portfoliowatcher.model

import dev.daniza.portfoliowatcher.model.moralis.MoralisTokenTrend

interface JsonResourceProvider {
    fun getTrendingTokensJson(): List<MoralisTokenTrend>
}