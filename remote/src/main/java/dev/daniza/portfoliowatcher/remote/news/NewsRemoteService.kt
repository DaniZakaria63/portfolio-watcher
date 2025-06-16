package dev.daniza.portfoliowatcher.remote.news

import dev.daniza.portfoliowatcher.model.news.NewsHeadline
import javax.inject.Inject

class NewsRemoteService @Inject constructor(
    private val remoteEndpoint: RemoteEndpoint
) : NewsRemote {
    override suspend fun getNewsHeadline(page: Int, pageSize: Int): List<NewsHeadline> {
        val result = remoteEndpoint.getTopHeadlines(
            url = DEFAULT_NEWS_REMOTE_BASE_URL + "top-headlines",
            page = page,
            page
        )
        return result.articles
    }
}