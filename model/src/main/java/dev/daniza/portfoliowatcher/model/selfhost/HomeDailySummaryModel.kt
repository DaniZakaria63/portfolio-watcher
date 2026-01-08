package dev.daniza.portfoliowatcher.model.selfhost

import com.google.gson.annotations.SerializedName

data class HomeDailySummaryModel(
    @SerializedName("candles")
    val candles: List<Double>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("price_opening")
    val priceOpening: Double? = null,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("gain")
    val gain: Float? = null,

    @SerializedName("current_price")
    val currentPrice: Double? = null,
)