package dev.daniza.portfoliowatcher.remote.news

import dev.daniza.portfoliowatcher.model.news.NewsHeadline


interface NewsRemote {
    suspend fun getNewsHeadline(
        page: Int = 1,
        pageSize: Int = 10,
    ): List<NewsHeadline>
}