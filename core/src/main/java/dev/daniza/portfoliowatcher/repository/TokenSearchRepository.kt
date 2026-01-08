/*
 * Portfolio Watcher - TokenSearchRepository.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Repository interface defining token search data access contract for cryptocurrency search functionality
 */

package dev.daniza.portfoliowatcher.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel
import dev.daniza.portfoliowatcher.pager.TokenSearchPagingSource
import dev.daniza.portfoliowatcher.remote.service.ConnectivityChecker
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TokenSearchRepository {
    suspend fun getSearchTokens(
        query: String,
    ): Flow<PagingData<TokenSearchModel>>
}

class TokenSearchRepositoryImpl @Inject constructor(
    val tokenMetricsRemote: TokenMetricsRemote,
    val connectivityChecker: ConnectivityChecker,
) : TokenSearchRepository {
    override suspend fun getSearchTokens(query: String): Flow<PagingData<TokenSearchModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 8
            ),
            pagingSourceFactory = {
                TokenSearchPagingSource(
                    query = query,
                    tokenMetricsService = tokenMetricsRemote,
                    connectivityChecker = connectivityChecker
                )
            }
        ).flow
    }
}