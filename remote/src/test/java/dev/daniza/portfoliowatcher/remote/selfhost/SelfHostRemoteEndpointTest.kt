package dev.daniza.portfoliowatcher.remote.selfhost

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import kotlinx.coroutines.test.runTest
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SelfHostRemoteEndpointTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var selfHostRemoteEndpoint: SelfHostRemoteEndpoint
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        gson = GsonBuilder().create()

        selfHostRemoteEndpoint = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SelfHostRemoteEndpoint::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testCheckTokenUserSession_Success() = runTest {
        val token = "test-token"
        val expectedResponse = SelfHostResponse(
            message = "Token is valid",
            data = UserSession(
                token = token,
                isActive = true
            )
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expectedResponse))
                .addHeader("Content-Type", "application/json")
        )

        val response = selfHostRemoteEndpoint.checkTokenUserSession(token.toRequestBody())

        assertThat(response.message).isEqualTo("Token is valid")
        assertThat(response.data?.token).isEqualTo(token)
        assertThat(response.data?.isActive).isTrue()
    }

    @Test
    fun testCheckTokenUserSession_NotFound() = runTest {
        val token = "invalid-token"

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("{\"error\": \"Endpoint not found\"}")
                .addHeader("Content-Type", "application/json")
        )

        try {
            selfHostRemoteEndpoint.checkTokenUserSession(token.toRequestBody())
        } catch (e: Exception) {
            assertThat(e.message).contains("HTTP 404")
        }
    }

    @Test
    fun testGetHomeDailySummaryData_Success() = runTest {
        val body = "{}".toRequestBody()
        val candle = HomeDailySummaryModel.HomeDailySummaryCandleModel(274.4342)
        val model = HomeDailySummaryModel(
            candles = listOf(candle),
            name = "Apple Inc.",
            priceOpening = 270.00,
            symbol = "AAPL",
            gain = 1.5f,
            currentPrice = 274.43
        )
        val expectedResponse = SelfHostResponse(
            message = "Data retrieved successfully",
            data = listOf(model)
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expectedResponse))
                .addHeader("Content-Type", "application/json")
        )

        val response = selfHostRemoteEndpoint.getHomeDailySummaryData(body)

        assertThat(response.message).isEqualTo("Data retrieved successfully")
        assertThat(response.data).isNotNull()
        assertThat(response.data?.size).isEqualTo(1)
        val item = response.data?.get(0)
        assertThat(item?.name).isEqualTo("Apple Inc.")
        assertThat(item?.symbol).isEqualTo("AAPL")
        assertThat(item?.priceOpening).isEqualTo(270.00)
        assertThat(item?.currentPrice).isEqualTo(274.43)
        assertThat(item?.gain).isEqualTo(1.5f)
        assertThat(item?.candles?.size).isEqualTo(1)
        assertThat(item?.candles?.get(0)?.closePrice).isEqualTo(274.4342)
    }

    @Test
    fun testGetHomeDailySummaryData_NotFound() = runTest {
        val body = "{}".toRequestBody()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("{\"error\": \"Endpoint not found\"}")
                .addHeader("Content-Type", "application/json")
        )

        try {
            selfHostRemoteEndpoint.getHomeDailySummaryData(body)
        } catch (e: Exception) {
            assertThat(e.message).contains("HTTP 404")
        }
    }

    @Test
    fun testGetHomeDailySummaryData_EmptyList() = runTest {
        val body = "{}".toRequestBody()
        val expectedResponse = SelfHostResponse(
            message = "No data available",
            data = emptyList<HomeDailySummaryModel>()
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expectedResponse))
                .addHeader("Content-Type", "application/json")
        )

        val response = selfHostRemoteEndpoint.getHomeDailySummaryData(body)

        assertThat(response.message).isEqualTo("No data available")
        assertThat(response.data).isNotNull()
        assertThat(response.data?.size).isEqualTo(0)
    }
}
