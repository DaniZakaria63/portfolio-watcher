package dev.daniza.portfoliowatcher.repository

import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadline(): Flow<PagingData<NewsEntity>>
}