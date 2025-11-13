package dev.daniza.portfoliowatcher.remote.selfhost

interface SelfHostRemote {
    suspend fun checkToken(token: String) : Result<String>
}