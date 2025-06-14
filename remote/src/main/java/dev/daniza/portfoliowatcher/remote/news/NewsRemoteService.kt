package dev.daniza.portfoliowatcher.remote.news

import dev.daniza.portfoliowatcher.model.news.NewsHeadline
import javax.inject.Inject

class NewsRemoteService @Inject constructor(
    private val remoteEndpoint: RemoteEndpoint
) : NewsRemote {
    override suspend fun getNewsHeadline(): List<NewsHeadline> {
        val result = remoteEndpoint.getTopHeadlines(DEFAULT_REMOTE_BASE_URL + "top-headlines")
        return result.articles
    }
}