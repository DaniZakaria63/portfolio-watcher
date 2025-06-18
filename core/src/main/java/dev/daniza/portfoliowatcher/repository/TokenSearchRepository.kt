package dev.daniza.portfoliowatcher.repository

import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel
import kotlinx.coroutines.flow.Flow

interface TokenSearchRepository {
    suspend fun getSearchTokens(
        query: String,
    ): Flow<PagingData<TokenSearchModel>>
}