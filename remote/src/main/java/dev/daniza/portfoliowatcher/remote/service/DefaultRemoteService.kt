package dev.daniza.portfoliowatcher.remote.service

import com.google.gson.JsonObject
import dev.daniza.portfoliowatcher.remote.endpoint.RemoteEndpoint
import retrofit2.Call
import javax.inject.Inject

class DefaultRemoteService @Inject constructor(
    private val remoteEndpoint: RemoteEndpoint
) : RemoteService {
    override suspend fun getCoins(): JsonObject {
        return remoteEndpoint.getCoins()
    }
}