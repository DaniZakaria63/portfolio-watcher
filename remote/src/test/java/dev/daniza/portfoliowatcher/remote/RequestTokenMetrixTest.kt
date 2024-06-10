package dev.daniza.portfoliowatcher.remote

import com.google.gson.GsonBuilder
import dev.daniza.portfoliowatcher.remote.endpoint.DEFAULT_REMOTE_BASE_URL
import dev.daniza.portfoliowatcher.remote.endpoint.RemoteEndpoint
import dev.daniza.portfoliowatcher.remote.service.RemoteService
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestTokenMetrixTest {
    lateinit var retrofitClient: RemoteEndpoint

    @Before
    fun setUp(){
        val retrofit = Retrofit.Builder()
            .baseUrl(DEFAULT_REMOTE_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder().addHeader("api_key", BuildConfig.API_KEY).build()
                        chain.proceed(request)
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        retrofitClient = retrofit.create(RemoteEndpoint::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkTheHeaderOfTheRequest() = runTest {
        val response = retrofitClient.getCoins()
        advanceUntilIdle()
        assertTrue(!response.isJsonNull)
        print(response)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkTheDefaultValue() = runTest {
        val response = retrofitClient.getCoins()
        advanceUntilIdle()
        val data = SelectedTokensStateHolder(
            state = "DONE",
            tokens = response.getAsJsonArray("data").map {
                (it.asJsonObject).get("TOKEN_NAME").asString
            }
        )
        assertThat(data.tokens.first(), `is`(data.defaultToken))
    }
}


data class SelectedTokensStateHolder(
    var state: String = "LOADING",
    var tokens: List<String> = listOf(),
){
    val defaultToken get() = tokens.takeIf { it.isNotEmpty() }?.first()
}