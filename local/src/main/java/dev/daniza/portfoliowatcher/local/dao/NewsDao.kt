package dev.daniza.portfoliowatcher.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import dev.daniza.portfoliowatcher.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>): List<Long>

    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllNews(): PagingSource<Int, NewsEntity>

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: Long): NewsEntity?

    @Update
    suspend fun update(news: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}
