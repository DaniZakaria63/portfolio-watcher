package dev.daniza.portfoliowatcher.repository
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.remote.parser.SelfHostResponse
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostRemoteEndpoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val selfHostRemoteEndpoint: SelfHostRemoteEndpoint,
): UserSessionRepository{
    override suspend fun getToken(): Flow<Result<UserSession>> =
        dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map { preferences ->
                val userToken = preferences[stringPreferencesKey(UserSession.NAME)].orEmpty()
                if(userToken.isBlank()) {
                    Result.failure(Exception("You has no token"))
                }else{
                    Result.success(
                        UserSession(token = userToken, updated_at = System.currentTimeMillis())
                    )
                }
        }

    override suspend fun updateToken(user: UserSession): Result<Unit> {
        try{
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(UserSession.NAME)] = user.token
            }
            return Result.success(Unit)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

    override suspend fun checkToken(token: String): Result<SelfHostResponse> {
        return Result.runCatching {
            selfHostRemoteEndpoint.checkTokenUserSession(token)
        }
    }
}