package dev.daniza.portfoliowatcher.local.dao

import androidx.room.*
import dev.daniza.portfoliowatcher.local.entity.SmallStockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SmallStockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstrument(instrument: SmallStockEntity): Long

    @Delete
    suspend fun deleteInstrument(instrument: SmallStockEntity): Int

    @Query("SELECT * FROM small_stock")
    fun getAllInstruments(): List<SmallStockEntity>

    @Query("DELETE FROM small_stock")
    suspend fun deleteAllInstruments(): Int
}
