/*
 * Portfolio Watcher - NewsRepository.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Repository interface defining news data access contract between data sources and use cases
 */

package dev.daniza.portfoliowatcher.repository

import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadline(): Flow<PagingData<NewsEntity>>
}