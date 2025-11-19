package dev.daniza.portfoliowatcher.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val selfHostRemote: SelfHostRemote,
): UserSessionRepository {
    override suspend fun getToken(): Flow<Result<UserSession>> = withContext(Dispatchers.IO) {
        dataStore.data.catch {
            exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map{ preferences ->
            val userToken = preferences[stringPreferencesKey(UserSession.NAME)].orEmpty()
            if (userToken.isBlank()) {
                Result.failure(Exception("You has no token"))
            } else {
                Result.success(
                    UserSession(token = userToken, updated_at = System.currentTimeMillis())
                )
            }
        }
    }

    override suspend fun updateToken(token: String): Result<Unit> = withContext(Dispatchers.IO) {
        Result.runCatching {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(UserSession.NAME)] = token
            }
            Unit
        }
    }

    override suspend fun checkTokenFromServer(token: String): Result<UserSession> {
        val response = withContext(Dispatchers.IO){
            Result.runCatching {
                selfHostRemote.checkTokenUserSession(token)
            }
        }
        if(response.isFailure) return Result.failure(
            response.exceptionOrNull()?: Exception("Failed to check token from server")
        )

        return response.map {
            UserSession(
                token = it.data?.token.orEmpty(),
                created_at = it.data?.created_at,
                updated_at = System.currentTimeMillis(),
                isActive = it.data?.isActive,
            )
        }
    }
}