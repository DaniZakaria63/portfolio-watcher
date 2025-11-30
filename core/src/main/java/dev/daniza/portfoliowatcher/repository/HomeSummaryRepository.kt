package dev.daniza.portfoliowatcher.repository

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HomeSummaryRepository {
    suspend fun getHomeDailySummaryData(
        token: String,
        symbols: List<String>
    ) : Result<List<HomeDailySummaryModel>>
}

class HomeSummaryRepositoryImpl @Inject constructor(
    private val selfHostRemote: SelfHostRemote,
) : HomeSummaryRepository {
    override suspend fun getHomeDailySummaryData(token: String, symbols: List<String>): Result<List<HomeDailySummaryModel>> {
        val response = withContext(Dispatchers.IO) {
            Result.runCatching {
                selfHostRemote.getHomeDailySummaryData(token, symbols)
            }
        }

        if (response.isFailure) return Result.failure(
            response.exceptionOrNull() ?: Exception("Failed to get home daily summary data from server")
        )

        return response.map { it.data.orEmpty() }
    }
}