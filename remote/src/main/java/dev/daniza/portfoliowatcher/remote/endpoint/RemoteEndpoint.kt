package dev.daniza.portfoliowatcher.remote.endpoint

import com.google.gson.JsonObject
import retrofit2.http.GET

const val DEFAULT_REMOTE_BASE_URL = "https://api.tokenmetrics.com/v2/"
interface RemoteEndpoint {

    @GET("coins")
    suspend fun getCoins() : JsonObject
}