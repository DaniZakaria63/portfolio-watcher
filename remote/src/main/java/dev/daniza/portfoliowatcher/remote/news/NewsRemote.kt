package dev.daniza.portfoliowatcher.remote.news

import dev.daniza.portfoliowatcher.model.news.NewsHeadline


interface NewsRemote {
    suspend fun getNewsHeadline(): List<NewsHeadline>
}