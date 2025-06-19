package dev.daniza.portfoliowatcher.remote

import com.google.gson.GsonBuilder
import dev.daniza.portfoliowatcher.remote.news.DEFAULT_NEWS_REMOTE_BASE_URL
import dev.daniza.portfoliowatcher.remote.news.NewsRemoteEndpoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class RequestNewsRemoteTest {
    var retrofitClient: NewsRemoteEndpoint? = null

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl(DEFAULT_NEWS_REMOTE_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
            )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            ).build()

        retrofitClient = retrofit.create(NewsRemoteEndpoint::class.java)
    }

    @After
    fun tearDown() {
        retrofitClient = null
    }

    @Test
    fun checkTheKeyOfTheRequest() = runTest {
        val response = retrofitClient?.getTopHeadlines(
            url = DEFAULT_NEWS_REMOTE_BASE_URL + "top-headlines",
            page = 1,
            pageSize = 10
        )
        advanceUntilIdle()
        assertThat(response?.status, `is`("ok"))
    }

    @Test
    fun checkTheActualNewsWillDisplay() = runTest {
        val response = retrofitClient?.getTopHeadlines(
            url = DEFAULT_NEWS_REMOTE_BASE_URL + "top-headlines",
            page = 1,
            pageSize = 10,
            category = "",
            query = "",
            sources = "crypto-coins-news"
        )

        advanceUntilIdle()
        assertThat(response?.status, `is`("ok"))
        assert((response?.totalResults ?: 0) > 0)
        response?.articles?.forEach { article ->
            println("Title: ${article.title}, Description: ${article.description}")
        }
    }
}