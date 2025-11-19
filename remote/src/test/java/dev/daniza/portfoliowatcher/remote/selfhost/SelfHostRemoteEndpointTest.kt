package dev.daniza.portfoliowatcher.remote.selfhost

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import kotlinx.coroutines.test.runTest
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
        // Given
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

        // When
        val response = selfHostRemoteEndpoint.checkTokenUserSession(token)

        // Then
        assertThat(response.message).isEqualTo("Token is valid")
        assertThat(response.data?.token).isEqualTo(token)
        assertThat(response.data?.isActive).isTrue()
    }

    @Test
    fun testCheckTokenUserSession_NotFound() = runTest {
        // Given
        val token = "invalid-token"

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("{\"error\": \"Endpoint not found\"}")
                .addHeader("Content-Type", "application/json")
        )

        // When & Then
        try {
            selfHostRemoteEndpoint.checkTokenUserSession(token)
        } catch (e: Exception) {
            assertThat(e.message).contains("HTTP 404")
        }
    }
}
