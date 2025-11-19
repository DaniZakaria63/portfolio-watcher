package dev.daniza.portfoliowatcher.remote.moralis

import dev.daniza.portfoliowatcher.model.moralis.MoralisTokenTrend

class MoralisRemoteService(
    private val remoteEndpoint: MoralisRemoteEndpoint
) : MoralisRemote {
    override suspend fun getTrendingTokens(limit: Int): List<MoralisTokenTrend> {
        return emptyList()
    }
}