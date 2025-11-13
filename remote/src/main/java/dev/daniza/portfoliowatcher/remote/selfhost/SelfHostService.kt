package dev.daniza.portfoliowatcher.remote.selfhost

class SelfHostService (
    private val selfHostRemoteEndpoint: SelfHostRemoteEndpoint
): SelfHostRemote {
    override suspend fun checkToken(token: String): Result<String> {
        return Result.success("Nice Dude")
    }
}