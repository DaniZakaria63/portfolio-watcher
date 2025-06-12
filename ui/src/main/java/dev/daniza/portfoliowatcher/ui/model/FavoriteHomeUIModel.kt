package dev.daniza.portfoliowatcher.ui.model

data class FavoriteHomeUIModel(
    val id: String,
    val name: String,
    val price: String,
    val changePercent: String,
    val chartData: List<Float>,
)