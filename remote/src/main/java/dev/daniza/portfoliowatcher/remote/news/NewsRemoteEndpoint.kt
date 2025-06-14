package dev.daniza.portfoliowatcher.remote.news

import dev.daniza.portfoliowatcher.remote.parser.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Url

const val DEFAULT_REMOTE_BASE_URL = "https://newsapi.org/v2/"
interface RemoteEndpoint {

    @GET
    suspend fun getTopHeadlines(@Url url: String): NewsResponse
}