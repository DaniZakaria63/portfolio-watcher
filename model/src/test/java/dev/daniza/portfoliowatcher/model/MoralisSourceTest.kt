package dev.daniza.portfoliowatcher.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.daniza.portfoliowatcher.model.moralis.CryptoTrendToken
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.InputStreamReader

class MoralisSourceTest {

    private lateinit var mockJsonProvider: JsonResourceProvider

    @Before
    fun setup() {

        // Load test data from JSON file
        val inputStream = javaClass.classLoader?.getResourceAsStream("moralis.trend.json")
        requireNotNull(inputStream) { "Could not find moralis.trend.json in test resources" }

        val reader = InputStreamReader(inputStream)
        val listType = object : TypeToken<List<CryptoTrendToken>>() {}.type
        val trendingTokens: List<CryptoTrendToken> = Gson().fromJson(reader, listType)

        // Set up mock provider
        mockJsonProvider = mockk()
        coEvery { mockJsonProvider.getTrendingTokensJson() } returns trendingTokens
    }

    @Test
    fun `test getTrendingTokens returns correct data`() = runBlocking {
        // When
        val result = mockJsonProvider.getTrendingTokensJson()

        // Then
        assertNotNull(result)
        assertEquals(128, result.size)

        // Verify first token
        val firstToken = result.first()
        assertEquals("0x1", firstToken.chainId)
        assertEquals("0x00148d918311aa2c363f752d45b57cff9200cff0", firstToken.tokenAddress)
        assertEquals("Brown", firstToken.name)
        assertEquals("BROWN", firstToken.symbol)
    }

    @Test
    fun `test select single last returns correct last token`() = runBlocking {
        // Given
        val address = "0xd7404f52ea7a2709dfef0994db3f26700705eaf4"

        // When
        val result = mockJsonProvider.getTrendingTokensJson().last()

        // Then
        assertNotNull(result)
        assertEquals(address, result.tokenAddress)
        assertEquals("Not Gonna Make It", result.name)
        assertEquals("NGMI", result.symbol)
    }
}
