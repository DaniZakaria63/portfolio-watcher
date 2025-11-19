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

import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel
import kotlinx.coroutines.flow.Flow

interface TokenSearchRepository {
    suspend fun getSearchTokens(
        query: String,
    ): Flow<PagingData<TokenSearchModel>>
}