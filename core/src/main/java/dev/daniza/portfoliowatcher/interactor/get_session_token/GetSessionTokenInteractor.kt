package dev.daniza.portfoliowatcher.interactor.get_session_token

import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionTokenInteractor @Inject constructor(
    private val userSessionRepository: UserSessionRepository
){
    suspend operator fun invoke(): Flow<Result<UserSession>> = userSessionRepository.getToken()
}