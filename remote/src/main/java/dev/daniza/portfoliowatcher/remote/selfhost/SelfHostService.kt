package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class SelfHostService (
    private val selfHostRemoteEndpoint: SelfHostRemoteEndpoint
): SelfHostRemote {
    override suspend fun checkTokenUserSession(token: String): SelfHostResponse<UserSession> {
        val jsonToken = JSONObject().apply {
            put("token", token)
        }.toString()
        return selfHostRemoteEndpoint.checkTokenUserSession(jsonToken.toRequestBody())
    }
}