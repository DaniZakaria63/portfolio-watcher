package dev.daniza.portfoliowatcher.remote.parser

import dev.daniza.portfoliowatcher.model.news.NewsHeadline

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsHeadline>
)