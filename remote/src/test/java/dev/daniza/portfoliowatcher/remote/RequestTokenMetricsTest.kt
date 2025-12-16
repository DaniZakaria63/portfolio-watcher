package dev.daniza.portfoliowatcher.remote

/**
 * Created by daniza on 2023/10/01.
 * Temporary Unavailable
 *
 **/
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TOKEN_METRICS_SEARCH_URL
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemoteEndpoint
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

class RequestTokenMetricsTest {
    lateinit var retrofitClient: TokenMetricsRemoteEndpoint

    @Before
    fun setUp(){
        val retrofit = Retrofit.Builder()
            .baseUrl(TOKEN_METRICS_SEARCH_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request =
                            chain.request().newBuilder().addHeader("x-api-key", BuildConfig.API_KEY_TOKENMETRICS)
                                .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setStrictness(Strictness.LENIENT).create()
                )
            )
            .build()

        retrofitClient = retrofit.create(TokenMetricsRemoteEndpoint::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkTheHeaderOfTheRequest() = runTest {
        val response = retrofitClient.getSearchTokens(
            url = TOKEN_METRICS_SEARCH_URL + "tokens",
            tokenName = "bitcoin"
        )
        advanceUntilIdle()
        assertTrue(response.success)
        print(response)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkTheDefaultValue() = runTest {
        val response = retrofitClient.getSearchTokens(
            url = TOKEN_METRICS_SEARCH_URL + "tokens",
            tokenName = "bitcoin"
        )
        advanceUntilIdle()
        assertTrue(response.success)
        assertThat(response.data.first().tokenName, `is`("Bitcoin"))
    }
}