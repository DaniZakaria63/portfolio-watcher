package dev.daniza.portfoliowatcher.ui.model

data class ItemNewsModel(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val url: String,
    val publishedAt: String,
    val sourceName: String? = null,
    val sourceLogoUrl: String? = null
)
