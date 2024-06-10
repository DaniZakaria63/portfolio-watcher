package dev.daniza.portfoliowatcher.remote.service

import com.google.gson.JsonObject

interface RemoteService {
    suspend fun getCoins() : JsonObject
}