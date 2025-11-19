/*
 * Portfolio Watcher - SplashViewModel.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Manages splash screen logic for Portfolio Watcher application
 */

package dev.daniza.portfoliowatcher.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_session_token.GetSessionTokenInteractor
import dev.daniza.portfoliowatcher.interactor.set_session_token.SetSessionTokenInteractor
import dev.daniza.portfoliowatcher.interactor.validate_session_token.ValidateSessionTokenInteractor
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.model.state.StateUI.Loading
import dev.daniza.portfoliowatcher.network.ConnectivityObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionTokenInteractor: GetSessionTokenInteractor,
    private val validateSessionTokenInteractor: ValidateSessionTokenInteractor,
    private val setSessionTokenInteractor: SetSessionTokenInteractor,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    /**
     * Observes the connectivity status of the device.
     * Shared as a Flow to be consumed by the UI.
     */
    val connectionStatus get() = connectivityObserver.isConnected.shareIn(viewModelScope, started = SharingStarted.Lazily)

    private val _tokenState: MutableSharedFlow<StateUI<Boolean>> = MutableSharedFlow()
    /**
     * Publicly exposed flow for observing the token validation state.
     * The data indicates whether showThe Welcome screen (true) or not (false).
     */
    val tokenState: SharedFlow<StateUI<Boolean>> = _tokenState.asSharedFlow()
        .shareIn(viewModelScope, started = SharingStarted.WhileSubscribed())

    /**
     * Retrieves the current session token.
     * If the token retrieval fails, it attempts to push a new token to the server.
     */
    fun getCurrentSession(){
        viewModelScope.launch (Dispatchers.Main){
            getSessionTokenInteractor().catch { throwable ->
                Log.e("ASD", "getCurrentSession: getSessionTokenInteractor: ", throwable)
                pushTokenServer(throwable.message.orEmpty().ifEmpty { "newbie" })
            }.collect { value ->
                value.onSuccess {
                    _tokenState.emit(
                        value = StateUI(
                            loading = Loading.DONE,
                            data = false,
                        )
                    )
                }.onFailure {
                    pushTokenServer(it.message.orEmpty().ifEmpty { "newbie" })
                }
            }
        }
    }

    /**
     * Validates the session token with the server.
     * If validation fails, emits an error state with a detailed message.
     * If validation succeeds, saves the token and emits a success state.
     *
     * @param token The session token to validate as new user.
     */
    fun pushTokenServer(token:String){
        viewModelScope.launch(Dispatchers.Main){
            validateSessionTokenInteractor(token).onFailure { exception ->
                Log.e("ASD", "pushTokenServer: validateSessionTokenInteractor: ", exception)
                val errorMessage = when {
                    exception.message?.contains("HTTP 404") == true -> "Server endpoint not found. Please check the API URL."
                    exception.message?.contains("timeout") == true -> "Request timed out. Please check your internet connection."
                    exception.message?.contains("HTTP 500") == true -> "Server error. Please try again later."
                    else -> exception.message.orEmpty().ifEmpty { "An unexpected error occurred" }
                }
                _tokenState.emit(StateUI(
                        loading = Loading.ERROR,
                        error = errorMessage
                    ))
            }.onSuccess { value ->
                withContext(Dispatchers.IO) { setSessionTokenInteractor(value.token) }
                _tokenState.emit(StateUI(
                    loading = Loading.DONE,
                    data = true
                ))
            }

        }
    }
}