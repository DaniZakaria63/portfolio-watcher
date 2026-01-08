package dev.daniza.portfoliowatcher.repository

import dev.daniza.portfoliowatcher.local.dao.SmallStockDao
import dev.daniza.portfoliowatcher.local.dao.StockDao
import dev.daniza.portfoliowatcher.local.entity.SmallStockEntity
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HomeSummaryRepository {
    suspend fun getHomeDailySummaryData(
        token: String,
        symbols: List<String>
    ) : Result<List<HomeDailySummaryModel>>

    suspend fun getHomeDailyChartData(
        token: String,
        symbol: String,
        range: String
    ) : Result<HomeDailyChartModel>

    suspend fun getHomeRecommendation(): Result<HomeRecommendation>

    suspend fun processTheStock(action: Int = 0, data: SmallStockEntity?): Result<List<SmallStockEntity>?>
}

class HomeSummaryRepositoryImpl @Inject constructor(
    private val selfHostRemote: SelfHostRemote,
    private val smallStockDao: SmallStockDao
) : HomeSummaryRepository {

    override suspend fun getHomeDailySummaryData(token: String, symbols: List<String>): Result<List<HomeDailySummaryModel>> {
        val response = withContext(Dispatchers.IO) {
            Result.runCatching {
                selfHostRemote.getHomeDailySummaryData(symbols)
            }
        }

        if (response.isFailure) return Result.failure(
            response.exceptionOrNull() ?: Exception("Failed to get home daily summary data from server")
        )

        return response.map { it.data.orEmpty() }
    }

    override suspend fun getHomeDailyChartData(
        token: String,
        symbol: String,
        range: String
    ): Result<HomeDailyChartModel> {
        val response = withContext(Dispatchers.IO){
            Result.runCatching {
                selfHostRemote.getHomeDailyChartData(token, symbol, range)
            }
        }
        if (response.isFailure) return Result.failure(
            response.exceptionOrNull() ?: Exception("Failed to get home daily chart data from server")
        )
        if(response.getOrNull()==null){
            return Result.failure(Exception("No data received from server"))
        }
        return response.map { it.data!!  }
    }

    override suspend fun getHomeRecommendation(): Result<HomeRecommendation> {
        val response = withContext(Dispatchers.IO){
            Result.runCatching {
                selfHostRemote.getHomeRecommendation()
            }
        }

        if (response.isFailure) return Result.failure(
            response.exceptionOrNull() ?: Exception("Failed to get home daily chart data from server")
        )

        if(response.getOrNull()==null){
            return Result.failure(Exception("No data received from server"))
        }

        return response
    }

    override suspend fun processTheStock(action: Int, data: SmallStockEntity?): Result<List<SmallStockEntity>?> {
        return when(action){
            1 -> { // Add One
                data?.let {
                    withContext(Dispatchers.IO){
                        smallStockDao.insertInstrument(it)
                    }
                }
                Result.success(null)
            }
            2 -> { // Delete All
                withContext(Dispatchers.IO){
                    smallStockDao.deleteAllInstruments()
                }
                Result.success(null)
            }
            else -> { // Get All
                Result.runCatching {
                    withContext(Dispatchers.IO){
                        smallStockDao.getAllInstruments()
                    }
                }
            }
        }
    }
}