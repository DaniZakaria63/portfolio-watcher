package dev.daniza.portfoliowatcher.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.daniza.portfoliowatcher.local.entity.StockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(coin: StockEntity)

    @Update
    suspend fun update(coin: StockEntity)

    @Query("SELECT * FROM stock ORDER BY name ASC")
    fun getAllCoins(): Flow<List<StockEntity>>

    @Query("SELECT * FROM stock WHERE is_favorite = 1 ORDER BY name ASC")
    fun getFavoriteCoins(): Flow<List<StockEntity>>

    @Query("SELECT * FROM stock WHERE id = :coinId")
    suspend fun getCoinById(coinId: String): StockEntity?

    @Query("DELETE FROM stock")
    suspend fun deleteAll()
}