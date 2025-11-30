package dev.daniza.portfoliowatcher.presenter

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_home_daily_summary.GetHomeDailySummaryInteractor
import dev.daniza.portfoliowatcher.interactor.get_session_token.GetSessionTokenInteractor
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.presenter.BuildConfig.TAG
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDailySummaryInteractor: GetHomeDailySummaryInteractor,
    private val getSessionTokenInteractor: GetSessionTokenInteractor,
) : ViewModel() {
    private val currentSampleSymbols = listOf("AAPL", "GOOGL", "AMZN", "TSLA")

    private val _currentDailySummaryState : MutableStateFlow<List<HomeDailySummaryModel>> = MutableStateFlow(emptyList())
    val currentDailySummaryState: StateFlow<List<HomeDailySummaryModel>> get() =
        _currentDailySummaryState.stateIn(
            scope = viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList()
        )

    fun getHomeDailySummaryData() {
        viewModelScope.launch {
            val token = getSessionToken().await()
            if (token == null) {
                //TODO: Clean Session and Redirect to Splash Screen
                Log.e(TAG, "getHomeDailySummaryData: Invalid Session Token")
                return@launch
            }

            getHomeDailySummaryInteractor(token.token, currentSampleSymbols)
                .onFailure { exception ->
                    Log.e(TAG, "getHomeDailySummaryData: ", exception)
                }.onSuccess {
                    _currentDailySummaryState.emit(value = it)
                }

        }
    }

    fun getSessionToken() = viewModelScope.async {
        getSessionTokenInteractor().catch {
            Log.e(TAG, "getSessionToken: ", it)
        }.firstOrNull()?.getOrNull()
    }
}