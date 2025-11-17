package dev.daniza.portfoliowatcher.repository

import dev.daniza.portfoliowatcher.model.session.UserSession
import kotlinx.coroutines.flow.Flow

interface UserSessionRepository{
    suspend fun getToken(): Flow<Result<UserSession>>
    suspend fun updateToken(user: UserSession): Result<Unit>
    suspend fun checkTokenFromServer(token: String): Result<UserSession>
}