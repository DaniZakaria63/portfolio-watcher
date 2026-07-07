package dev.daniza.portfoliowatcher.remote.selfhost

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.model.selfhost.DailyData
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
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
/*

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
*/

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
/*

    @Test
    fun testGetHomeDailyChartData_Success() = runTest {
        val body = "{}".toRequestBody()
        val expectedResponse = SelfHostResponse(
            message = "Chart data retrieved successfully",
            data = HomeDailyChartModel(
                metadata = Metadata(
                    information = "Daily Prices (open, high, low, close) and Volumes",
                    symbol = "IBM",
                    last_refreshed = "2025-12-01",
                    output_size = "Compact",
                    timezone = "US/Eastern"
                ),
                data = listOf(
                    DailyData(
                        date = 1733011200000L,
                        open = 306.505,
                        high = 307.12,
                        low = 302.8,
                        close = 305.67,
                        volume = 3166555
                    )
                )
            )
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expectedResponse))
                .addHeader("Content-Type", "application/json")
        )

        val response = selfHostRemoteEndpoint.getHomeDailyChartData(body)

        assertThat(response.message).isEqualTo("Chart data retrieved successfully")
        assertThat(response.data?.metadata?.symbol).isEqualTo("IBM")
        assertThat(response.data?.data?.size).isEqualTo(1)
        assertThat(response.data?.data?.get(0)?.close).isEqualTo(305.67)
    }
*/

    @Test
    fun testGetHomeDailyChartData_InvalidToken() = runTest {
        val body = "{}".toRequestBody()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("{\"error\": \"Invalid token\"}")
                .addHeader("Content-Type", "application/json")
        )

        try {
            selfHostRemoteEndpoint.getHomeDailyChartData(body)
        } catch (e: Exception) {
            assertThat(e.message).contains("HTTP 401")
        }
    }

    @Test
    fun testGetHomeDailyChartData_EmptyResponse() = runTest {
        val body = "{}".toRequestBody()
        val expectedResponse = SelfHostResponse<HomeDailyChartModel>(
            message = "No data available",
            data = null
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expectedResponse))
                .addHeader("Content-Type", "application/json")
        )

        val response = selfHostRemoteEndpoint.getHomeDailyChartData(body)

        assertThat(response.message).isEqualTo("No data available")
        assertThat(response.data).isNull()
    }

    @Test
    fun testGetHomeDailyChartData_ServerError() = runTest {
        val body = "{}".toRequestBody()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{\"error\": \"Internal Server Error\"}")
                .addHeader("Content-Type", "application/json")
        )

        try {
            selfHostRemoteEndpoint.getHomeDailyChartData(body)
        } catch (e: Exception) {
            assertThat(e.message).contains("HTTP 500")
        }
    }

    @Test
    fun testGetHomeDailyChartData_MalformedJson() = runTest {
        val body = "{}".toRequestBody()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{invalid json}")
                .addHeader("Content-Type", "application/json")
        )

        try {
            selfHostRemoteEndpoint.getHomeDailyChartData(body)
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(Exception::class.java)
        }
    }
/*
    @Test
    fun testGetHomeDailyChartData_EmptyCandles() = runTest {
        val body = "{}".toRequestBody()
        val expectedResponse = SelfHostResponse(
            message = "Chart data retrieved successfully",
            data = HomeDailyChartModel(
                metadata = Metadata(
                    information = "Daily Prices (open, high, low, close) and Volumes",
                    symbol = "IBM",
                    last_refreshed = "2025-12-01",
                    output_size = "Compact",
                    timezone = "US/Eastern"
                ),
                data = emptyList()
            )
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expectedResponse))
                .addHeader("Content-Type", "application/json")
        )

        val response = selfHostRemoteEndpoint.getHomeDailyChartData(body)

        assertThat(response.message).isEqualTo("Chart data retrieved successfully")
        assertThat(response.data?.metadata?.symbol).isEqualTo("IBM")
        assertThat(response.data?.data?.size).isEqualTo(0)
    }*/
}
