package dev.daniza.portfoliowatcher.model

import dev.daniza.portfoliowatcher.model.moralis.CryptoTrendToken

interface JsonResourceProvider {
    fun getTrendingTokensJson(): List<CryptoTrendToken>
}