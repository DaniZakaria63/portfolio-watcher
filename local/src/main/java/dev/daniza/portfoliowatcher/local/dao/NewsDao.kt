package dev.daniza.portfoliowatcher.local.dao

import androidx.room.*
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>): List<Long>

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: Long): NewsEntity?

    @Update
    suspend fun update(news: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}
