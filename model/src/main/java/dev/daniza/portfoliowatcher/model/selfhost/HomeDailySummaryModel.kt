package dev.daniza.portfoliowatcher.model.selfhost

import com.google.gson.annotations.SerializedName

data class HomeDailySummaryModel(
    @SerializedName("candles")
    val candles: List<HomeDailySummaryCandleModel>,

    @SerializedName("name")
    val name: String,

    @SerializedName("price_opening")
    val priceOpening: Double,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("gain")
    val gain: Float,
    @SerializedName("current_price")
    val currentPrice: Double,
) {
    data class HomeDailySummaryCandleModel(
        @SerializedName("close_price")
        val closePrice: Double,
    )
}
