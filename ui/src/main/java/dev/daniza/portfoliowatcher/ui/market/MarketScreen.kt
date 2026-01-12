package dev.daniza.portfoliowatcher.ui.market

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.itemKey
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.selfhost.Recommendation
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.MarketViewModel
import dev.daniza.portfoliowatcher.ui.component.Instrument
import dev.daniza.portfoliowatcher.ui.component.SearchDialog

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel(),
    isActivation: Boolean = false,
    modifier: Modifier = Modifier
) {
    val marketRecommendation by viewModel.currentMarketRecommendation.collectAsStateWithLifecycle()
    val marketPopular by viewModel.currentMarketPopular.collectAsStateWithLifecycle()

    val rememberLazyState = rememberLazyListState()
    LazyColumn(
        state = rememberLazyState
    ){

        when(marketRecommendation){
            is StateUI.Data -> {
                val currentData = (marketRecommendation as StateUI.Data<List<Recommendation.Quotes>>).value
                items(count = currentData.size){

                }
            }
            is StateUI.Error -> {

            }
            is StateUI.Loading-> {}
        }

        when(marketPopular){
            is StateUI.Data -> {
                val currentData = (marketPopular as StateUI.Data<List<MarketPopularModel.SmallQuote>>).value
                items(count = currentData.size){

                }
            }
            is StateUI.Error -> {

            }
            is StateUI.Loading-> {}
        }
    }

    var activationSearchDialog by rememberSaveable { mutableStateOf(isActivation)}
    if(activationSearchDialog) {
        SearchDialog(
            onDismiss = { activationSearchDialog = false },
            onInstrumentSelected = { _ ->
                // Handle selection, e.g., update active instrument
            },
            initialInstruments = listOf(
                Instrument("CRWD", "CrowdStrike Holdings", "Equity", 390.16, 1.94),
                Instrument("BBY", "Best Buy Co.", "Equity", 75.20, -13.30),
                Instrument("ES=F", "E-Mini S&P 500 Mar 25", "Futures", 5829.25, 0.69),
                Instrument("CRDO", "Ceridian HCM Holding", "Equity", 54.32, 7.74),
                Instrument("MSTR", "MicroStrategy Inc.", "Equity", 275.15, 9.66)
            )
        )
    }
}