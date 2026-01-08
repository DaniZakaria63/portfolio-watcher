package dev.daniza.portfoliowatcher.model.selfhost

import com.google.gson.annotations.SerializedName

data class HomeDailyChartModel(
    @SerializedName("informational")
    val informational: Informational,

    @SerializedName("last_refreshed")
    val lastRefreshed: String,

    @SerializedName("timeframe")
    val timeframe: String,

    @SerializedName("tickers")
    val tickers: List<Ticker>
){
    data class Informational(
        @SerializedName("symbol")
        val symbol: String,

        @SerializedName("current_price")
        val currentPrice: Double,

        @SerializedName("day_gain_price")
        val dayGainPrice: Double,

        @SerializedName("day_gain_percent")
        val dayGainPercent: Double
    )

    data class Ticker(
        @SerializedName("date")
        val date: Long,  // Unix timestamp (seconds)

        @SerializedName("price")
        val price: Double
    )
}

data class DailyData(
    @SerializedName("date") val date: Long? = null,
    @SerializedName("open") val open: Double? = null,
    @SerializedName("high") val high: Double? = null,
    @SerializedName("low") val low: Double? = null,
    @SerializedName("close") val close: Double? = null,
    @SerializedName("volume") val volume: Long? = null
)
