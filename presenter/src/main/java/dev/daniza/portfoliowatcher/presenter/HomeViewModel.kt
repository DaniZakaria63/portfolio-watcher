package dev.daniza.portfoliowatcher.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_home_daily_chart.GetHomeDailyChartInteractor
import dev.daniza.portfoliowatcher.interactor.get_home_daily_summary.GetHomeDailySummaryInteractor
import dev.daniza.portfoliowatcher.interactor.get_session_token.GetSessionTokenInteractor
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.BuildConfig.TAG
import dev.daniza.portfoliowatcher.presenter.state.HomeDailyChartDataState
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
    private val getHomeDailyChartInteractor: GetHomeDailyChartInteractor,
) : ViewModel() {
    private val currentSampleSymbols = listOf("AAPL", "GOOGL", "AMZN", "TSLA")
    private var currentTokenSession: UserSession? = null

    private val _currentDailySummaryState : MutableStateFlow<List<HomeDailySummaryModel>> = MutableStateFlow(emptyList())
    val currentDailySummaryState: StateFlow<List<HomeDailySummaryModel>> get() =
        _currentDailySummaryState.stateIn(
            scope = viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList()
        )

    private val _currentDailyChartState : MutableStateFlow<StateUI<HomeDailyChartDataState>> = MutableStateFlow(StateUI.Loading)
    val currentDailyChartState: StateFlow<StateUI<HomeDailyChartDataState>> get() =
        _currentDailyChartState.stateIn(
            scope = viewModelScope, started = SharingStarted.Lazily, initialValue = StateUI.Loading
        )

    fun getHomeDailySummaryData() {
        viewModelScope.launch {
            currentTokenSession = getSessionToken().await()
            if (currentTokenSession == null) {
                //TODO: Clean Session and Redirect to Splash Screen
                Log.e(TAG, "getHomeDailySummaryData: Invalid Session Token")
                return@launch
            }


            getHomeDailySummaryInteractor(token = currentTokenSession?.token.orEmpty(), currentSampleSymbols)
                .onFailure { exception ->
                    Log.e(TAG, "getHomeDailySummaryData: ", exception)
                }.onSuccess {
                    _currentDailySummaryState.emit(value = it)
                    getHomeDailyChartData(
                        symbol = it[0].symbol,
                        range = HomeDailyChartDataState.RangeDate.DAILY
                    )
                }

        }
    }

    fun getHomeDailyChartData(
        symbol: String? = null,
        range: HomeDailyChartDataState.RangeDate = HomeDailyChartDataState.RangeDate.DAILY
    ) {
        viewModelScope.launch {
            getHomeDailyChartInteractor(
                token = currentTokenSession?.token.orEmpty(),
                symbol = symbol.orEmpty().ifEmpty { currentSampleSymbols[0] },
                range = range.param
            ).onFailure { exception ->
                Log.e(TAG, "getHomeDailyChartData: ", exception)
                _currentDailyChartState.value = StateUI.Error(exception)
            }.onSuccess { chartData ->
                val updatedState = HomeDailyChartDataState(
                    symbol = chartData.metadata?.symbol.orEmpty().ifEmpty { symbol },
                    range = range,
                    data = chartData
                )
                _currentDailyChartState.value = StateUI.Data(updatedState)
            }
        }
    }

    fun getSessionToken() = viewModelScope.async {
        getSessionTokenInteractor().catch {
            Log.e(TAG, "getSessionToken: ", it)
        }.firstOrNull()?.getOrNull()
    }
}