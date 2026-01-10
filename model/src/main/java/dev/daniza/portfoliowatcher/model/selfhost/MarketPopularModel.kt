package dev.daniza.portfoliowatcher.model.selfhost

import com.google.gson.annotations.SerializedName

data class MarketPopularModel(
    @SerializedName("trending") val trending: List<SmallQuote> = emptyList(),
    @SerializedName("small_cap") val smallCap: List<SmallQuote> = emptyList(),
    @SerializedName("large_cap") val largeCap: List<SmallQuote> = emptyList(),
    @SerializedName("most_active") val mostActive: List<SmallQuote> = emptyList(),
) {

    data class SmallQuote(
        @SerializedName("typeDisp") val type: String,
        @SerializedName("regularMarketChangePercent") val changePercent: Double,
        @SerializedName("regularMarketChange") val change: Double,
        @SerializedName("regularMarketPrice") val price: Double,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("displayName") val name: String,
        @SerializedName("shortName") val shortName: String
    )
}