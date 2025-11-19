/*
 * Portfolio Watcher - UserSessionRepository.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Repository interface defining user session management operations including token validation and storage
 */

package dev.daniza.portfoliowatcher.repository

import dev.daniza.portfoliowatcher.model.session.UserSession
import kotlinx.coroutines.flow.Flow

interface UserSessionRepository{
    suspend fun getToken(): Flow<Result<UserSession>>
    suspend fun updateToken(token: String): Result<Unit>
    suspend fun checkTokenFromServer(token: String): Result<UserSession>
}