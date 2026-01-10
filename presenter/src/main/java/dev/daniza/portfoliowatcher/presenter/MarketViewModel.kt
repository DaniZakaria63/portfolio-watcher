package dev.daniza.portfoliowatcher.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.daniza.portfoliowatcher.interactor.get_home_recommendation.GetHomeRecommendationInteractor
import dev.daniza.portfoliowatcher.interactor.get_market_recommendation.GetMarketRecommendationInteractor
import dev.daniza.portfoliowatcher.model.selfhost.HomeRecommendation
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.selfhost.Recommendation
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.BuildConfig.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getMarketRecommendationInteractor: GetMarketRecommendationInteractor,
    private val getHomeRecommendationInteractor: GetHomeRecommendationInteractor,
) : ViewModel(){
    private val popCategories = listOf("Trending", "Big Capital", "Small Capital", "Most Active")
    val currentPopMarketSelectable: MutableStateFlow<String> = MutableStateFlow(popCategories[0])
    private val _currentMarketPopuler: MutableStateFlow<StateUI<MarketPopularModel>> =
        MutableStateFlow(StateUI.Loading)
    val currentMarketPopular: StateFlow<StateUI<List<MarketPopularModel.SmallQuote>>> get()=
        combine(
            _currentMarketPopuler,
            currentPopMarketSelectable
        ){ state, filter ->
            when(state){
                is StateUI.Data -> {
                    val filteredData = when(filter){
                        recomCategories[0] -> state.value.trending
                        recomCategories[1] -> state.value.largeCap
                        recomCategories[2] -> state.value.smallCap
                        recomCategories[3] -> state.value.mostActive
                        else -> emptyList<MarketPopularModel.SmallQuote>()
                    }
                    StateUI.Data(filteredData)
                }
                is StateUI.Loading -> StateUI.Loading
                is StateUI.Error -> StateUI.Error(state.throwable)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), StateUI.Loading)


    private val recomCategories = listOf("Top Gainer", "Top Loser")
    val currentMarketSelectable: MutableStateFlow<String> = MutableStateFlow(recomCategories[0])
    private val _currentMarketRecommendation: MutableStateFlow<StateUI<HomeRecommendation>> =
        MutableStateFlow(StateUI.Loading)
    val currentMarketRecommendation: StateFlow<StateUI<List<Recommendation.Quotes>>>
        get() = combine(
            currentMarketSelectable,
            _currentMarketRecommendation,
        ){ state, market ->
            when(market){
                is StateUI.Data -> {
                    val gainers = market.value.topGainers?.quotes.orEmpty()
                    val losers = market.value.topLosers?.quotes.orEmpty()

                    val filteredData = when (state) {
                        recomCategories[0] -> gainers
                        recomCategories[1] -> losers
                        else -> emptyList<Recommendation.Quotes>()
                    }
                    StateUI.Data(filteredData)
                }
                is StateUI.Loading -> StateUI.Loading
                is StateUI.Error -> StateUI.Error(market.throwable)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), StateUI.Loading)

    init {
        this.getCurrentMarketRecommendation()
        this.getCurrentMarketPopular()
    }

    fun getCurrentMarketRecommendation(){
        viewModelScope.launch {
            getHomeRecommendationInteractor()
                .onSuccess {
                    _currentMarketRecommendation.emit(StateUI.Data(it))
                }.onFailure {
                    _currentMarketRecommendation.emit(StateUI.Error(it))
                    Log.e(TAG, "getCurrentMarketRecommendation: ", it)
                }
        }
    }

    fun getCurrentMarketPopular(){
        viewModelScope.launch {
            getMarketRecommendationInteractor()
                .onSuccess {
                    _currentMarketPopuler.emit(StateUI.Data(it))
                }.onFailure {
                    _currentMarketPopuler.emit(StateUI.Error(it))
                    Log.e(TAG, "getCurrentMarketPopular: ", it)
                }
        }
    }
}