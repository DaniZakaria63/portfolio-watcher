package dev.daniza.portfoliowatcher.remote.news

import dev.daniza.portfoliowatcher.model.news.NewsHeadline

class NewsRemoteService(
    private val remoteEndpoint: NewsRemoteEndpoint
) : NewsRemote {
    override suspend fun getNewsHeadline(page: Int, pageSize: Int): List<NewsHeadline> {
        val result = remoteEndpoint.getTopHeadlines(
            url = DEFAULT_NEWS_REMOTE_BASE_URL + "top-headlines",
            page = page,
            pageSize = page
        )
        return result.articles
    }
}