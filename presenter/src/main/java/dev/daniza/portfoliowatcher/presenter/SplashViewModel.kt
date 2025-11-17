package dev.daniza.portfoliowatcher.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_session_token.GetSessionTokenInteractor
import dev.daniza.portfoliowatcher.interactor.validate_session_token.ValidateSessionTokenInteractor
import dev.daniza.portfoliowatcher.model.parser.isTrue
import dev.daniza.portfoliowatcher.model.session.UserSession
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
    private val connectivityObserver: ConnectivityObserver,
): ViewModel(){
    val connectionStatus get() = connectivityObserver.isConnected.shareIn(viewModelScope, started = SharingStarted.Lazily)

    private val _isShowWelcome: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isShowWelcome: SharedFlow<Boolean> = _isShowWelcome.asSharedFlow()
        .shareIn(viewModelScope, started = SharingStarted.WhileSubscribed())

    fun getCurrentSession(){
        viewModelScope.launch (Dispatchers.IO){
            getSessionTokenInteractor().catch { it ->
                withContext(Dispatchers.Main){
                    _isShowWelcome.emit(false)
                }
            }.collect { value ->
                pushTokenServer(
                    value.getOrDefault(UserSession()).token
                )
            }
        }
    }

    fun pushTokenServer(token:String){
        viewModelScope.launch(Dispatchers.Main){
            validateSessionTokenInteractor(token).onFailure { exception ->
                exception.printStackTrace()
                _isShowWelcome.emit(false)
            }.onSuccess { value ->
                _isShowWelcome.emit(value.isNewUpdate.isTrue())
            }

        }
    }
}