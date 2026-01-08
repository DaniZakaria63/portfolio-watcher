package dev.daniza.portfoliowatcher.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.daniza.portfoliowatcher.local.entity.StockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(coin: StockEntity)

    @Query("SELECT * FROM stock ORDER BY name ASC")
    fun getAllStocks(): Flow<List<StockEntity>>

    @Query("SELECT * FROM stock WHERE symbol = :symbol")
    suspend fun getQuotesById(symbol: String): StockEntity?

    @Query("DELETE FROM stock")
    suspend fun deleteAll()
}