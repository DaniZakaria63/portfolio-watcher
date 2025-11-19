package dev.daniza.portfoliowatcher.interactor.set_session_token

import dev.daniza.portfoliowatcher.repository.UserSessionRepository
import javax.inject.Inject

class SetSessionTokenInteractor @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {
    suspend operator fun invoke(newToken: String) = userSessionRepository.updateToken(newToken)
}