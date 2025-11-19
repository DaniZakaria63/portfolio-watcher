/*
 * Portfolio Watcher - NewsDao.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Data Access Object defining database operations for news entity management and pagination
 */

package dev.daniza.portfoliowatcher.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
