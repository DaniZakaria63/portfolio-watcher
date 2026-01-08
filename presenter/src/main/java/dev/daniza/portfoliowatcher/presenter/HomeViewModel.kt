package dev.daniza.portfoliowatcher.presenter

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_home_daily_chart.GetHomeDailyChartInteractor
import dev.daniza.portfoliowatcher.interactor.get_home_daily_summary.GetHomeDailySummaryInteractor
import dev.daniza.portfoliowatcher.interactor.get_home_recommendation.GetHomeRecommendationInteractor
import dev.daniza.portfoliowatcher.interactor.get_session_token.GetSessionTokenInteractor
import dev.daniza.portfoliowatcher.interactor.process_small_stock_db.ProcessSmallStockInteractor
import dev.daniza.portfoliowatcher.local.entity.SmallStockEntity
import dev.daniza.portfoliowatcher.model.getCurrentTimeEpoch
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
import dev.daniza.portfoliowatcher.model.selfhost.Recommendation
import dev.daniza.portfoliowatcher.model.session.UserSession
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.BuildConfig.TAG
import dev.daniza.portfoliowatcher.presenter.state.HomeDailyChartDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDailySummaryInteractor: GetHomeDailySummaryInteractor,
    private val getSessionTokenInteractor: GetSessionTokenInteractor,
    private val getHomeDailyChartInteractor: GetHomeDailyChartInteractor,
    private val getHomeRecommendationInteractor: GetHomeRecommendationInteractor,
    private val processSmallStockInteractor: ProcessSmallStockInteractor,
) : ViewModel() {
    val categories = listOf("All", "Gainers", "Losers")
    private var _currentFavoriteStockSymbols by mutableStateOf(emptyList<String>())
    private var currentTokenSession: UserSession? = null

    private val _currentDailySummaryState: MutableStateFlow<List<HomeDailySummaryModel>> =
        MutableStateFlow(emptyList())
    val currentDailySummaryState: StateFlow<List<HomeDailySummaryModel>>
        get() =
            _currentDailySummaryState.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )

    private val _currentDailyChartState: MutableStateFlow<StateUI<HomeDailyChartDataState>> =
        MutableStateFlow(StateUI.Loading)
    val currentDailyChartState: StateFlow<StateUI<HomeDailyChartDataState>>
        get() =
            _currentDailyChartState.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = StateUI.Loading
            )

    val currentDailyGainLoseSelectable: MutableStateFlow<String> = MutableStateFlow(categories[0])
    private val _currentDailyGainLoseState: MutableStateFlow<StateUI<HomeRecommendation>> =
        MutableStateFlow(StateUI.Loading)
    val currentDailyDailyGainLoseState: StateFlow<StateUI<List<Recommendation.Quotes>>>
        get() =
            combine(
                _currentDailyGainLoseState,
                currentDailyGainLoseSelectable
            ) { state, filter ->
                when (state) {
                    is StateUI.Data -> {
                        val gainers = state.value.topGainers?.quotes.orEmpty()
                        val losers = state.value.topLosers?.quotes.orEmpty()

                        val filteredData = when (filter) {
                            categories[1] -> gainers
                            categories[2] -> losers
                            categories[0] -> gainers + losers
                            else -> emptyList<Recommendation.Quotes>()
                        }
                        StateUI.Data(filteredData)
                    }

                    is StateUI.Loading -> StateUI.Loading
                    is StateUI.Error -> StateUI.Error(state.throwable)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = StateUI.Loading
            )

    init {
        this.getStockCacheList()
        this.getHomeDailySummaryData()
        this.getHomeStockRecommendations()
    }

    fun getHomeStockRecommendations() {
        viewModelScope.launch {
            getHomeRecommendationInteractor().onFailure {
                Log.e(TAG, "getHomeStockRecommendations: ", it)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _currentDailyGainLoseState.emit(StateUI.Data(it))
                }
            }
        }
    }

    fun getHomeDailySummaryData() {
        viewModelScope.launch {
            currentTokenSession = getSessionToken().await()
            if (currentTokenSession == null) {
                processSmallStockInteractor(2, null) // Delete saved stocks
                //TODO: Clean Session and Redirect to Splash Screen
                Log.e(TAG, "getHomeDailySummaryData: Invalid Session Token")
                return@launch
            }

            getHomeDailySummaryInteractor(
                token = currentTokenSession?.token.orEmpty(),
                symbols = _currentFavoriteStockSymbols
            ).onFailure { exception ->
                Log.e(TAG, "getHomeDailySummaryData: ", exception)
            }.onSuccess {
                _currentDailySummaryState.emit(value = it)
                it.getOrNull(0)?.let { data ->
                    getHomeDailyChartData(
                        symbol = data.symbol,
                        range = HomeDailyChartDataState.RangeDate.M15
                    )
                }
            }
        }
    }

    fun getHomeDailyChartData(
        symbol: String? = null,
        range: HomeDailyChartDataState.RangeDate = HomeDailyChartDataState.RangeDate.H1
    ) {
        viewModelScope.launch {
            getHomeDailyChartInteractor(
                symbol = symbol.orEmpty().ifEmpty {
                    _currentFavoriteStockSymbols.firstOrNull().orEmpty().ifEmpty { "AAPL" }
                },
                range = range.param
            ).onFailure { exception ->
                Log.e(TAG, "getHomeDailyChartData: ", exception)
                _currentDailyChartState.value = StateUI.Error(exception)
            }.onSuccess { chartData ->
                val updatedState = HomeDailyChartDataState(
                    symbol = chartData.informational.symbol.ifEmpty { symbol },
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

    fun getStockCacheList() {
        viewModelScope.launch {
            val cacheStockList = async {
                processSmallStockInteractor(0, null)
            }.await().getOrDefault(defaultValue = emptyList())?.map {
                it.symbol
            }.orEmpty()

            _currentFavoriteStockSymbols = cacheStockList
        }

    }

    fun addStockWatchList(symbol: String) {
        if (symbol in _currentFavoriteStockSymbols) return

        viewModelScope.launch {
            processSmallStockInteractor(
                action = 1,
                data = SmallStockEntity(
                    name = symbol,
                    symbol = symbol,
                    type = symbol,
                    lastUpdated = getCurrentTimeEpoch()
                )
            ).onFailure {
                Log.e(TAG, "addStockWatchList: ", it)
            }.onSuccess {
                Log.i(TAG, "addStockWatchList: ADD STOCK SUCCESS $symbol")
                getStockCacheList()
            }
        }
    }

    fun updateTheGainLoseState(state: String) {
        viewModelScope.launch {
            currentDailyGainLoseSelectable.emit(state)
        }
    }
}