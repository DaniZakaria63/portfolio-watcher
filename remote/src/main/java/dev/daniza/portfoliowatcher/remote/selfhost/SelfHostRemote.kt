package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse

interface SelfHostRemote {
    suspend fun checkTokenUserSession(token: String) : SelfHostResponse<UserSession>
}