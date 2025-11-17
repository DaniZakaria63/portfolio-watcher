package dev.daniza.portfoliowatcher.interactor.validate_session_token

import dev.daniza.portfoliowatcher.repository.UserSessionRepository
import javax.inject.Inject

class ValidateSessionTokenInteractor @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {
    suspend operator fun invoke(token: String) = userSessionRepository.checkTokenFromServer(token)
}