package dev.daniza.portfoliowatcher.local.dao

import androidx.room.*
import dev.daniza.portfoliowatcher.local.entity.InstrumentSmall
import kotlinx.coroutines.flow.Flow

@Dao
interface InstrumentSmallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstrument(instrument: InstrumentSmall): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstruments(instruments: List<InstrumentSmall>): List<Long>

    @Update
    suspend fun updateInstrument(instrument: InstrumentSmall): Int

    @Delete
    suspend fun deleteInstrument(instrument: InstrumentSmall): Int

    @Query("SELECT * FROM instruments_small")
    fun getAllInstruments(): Flow<List<InstrumentSmall>>

    @Query("SELECT * FROM instruments_small WHERE id = :instrumentId")
    suspend fun getInstrumentById(instrumentId: String): InstrumentSmall?

    @Query("SELECT * FROM instruments_small WHERE type = :type")
    fun getInstrumentsByType(type: String): Flow<List<InstrumentSmall>>

    @Query("DELETE FROM instruments_small")
    suspend fun deleteAllInstruments(): Int
}
