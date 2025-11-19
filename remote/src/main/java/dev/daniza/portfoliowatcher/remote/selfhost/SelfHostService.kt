package dev.daniza.portfoliowatcher.remote.selfhost

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse

class SelfHostService (
    private val selfHostRemoteEndpoint: SelfHostRemoteEndpoint
): SelfHostRemote {
    override suspend fun checkTokenUserSession(token: String): SelfHostResponse<UserSession> {
        return selfHostRemoteEndpoint.checkTokenUserSession(token)
    }
}