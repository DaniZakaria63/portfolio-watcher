package dev.daniza.portfoliowatcher.model.moralis

data class CryptoTrendToken(
    val chainId: String,
    val tokenAddress: String,
    val name: String,
    val uniqueName: String,
    val symbol: String,
    val decimals: Int,
    val logo: String?,
    val usdPrice: Double,
    val createdAt: Long,
    val marketCap: Long,
    val liquidityUsd: Long,
    val holders: Int,
    val pricePercentChange: Map<String, Double>,
    val totalVolume: Map<String, Long>,
    val transactions: Map<String, Int>,
    val buyTransactions: Map<String, Int>,
    val sellTransactions: Map<String, Int>,
    val buyers: Map<String, Int>,
    val sellers: Map<String, Int>
)