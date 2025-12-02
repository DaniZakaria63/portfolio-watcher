package dev.daniza.portfoliowatcher.model.selfhost

import com.google.gson.annotations.SerializedName

data class HomeDailyChartModel(
    @SerializedName("metadata") val metadata: Metadata? = null,
    @SerializedName("data") val data: List<DailyData>? = null
)

data class Metadata(
    @SerializedName("information") val information: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("last_refreshed") val last_refreshed: String? = null,
    @SerializedName("output_size") val output_size: String? = null,
    @SerializedName("timezone") val timezone: String? = null
)

data class DailyData(
    @SerializedName("date") val date: Long? = null,
    @SerializedName("open") val open: Double? = null,
    @SerializedName("high") val high: Double? = null,
    @SerializedName("low") val low: Double? = null,
    @SerializedName("close") val close: Double? = null,
    @SerializedName("volume") val volume: Long? = null
)
