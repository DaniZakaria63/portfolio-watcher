package dev.daniza.portfoliowatcher.ui.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.selfhost.Recommendation
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.MarketViewModel
import dev.daniza.portfoliowatcher.ui.component.CardOverview
import dev.daniza.portfoliowatcher.ui.component.FilterButton
import dev.daniza.portfoliowatcher.ui.component.Instrument
import dev.daniza.portfoliowatcher.ui.component.SearchDialog
import dev.daniza.portfoliowatcher.ui.component.TickerItemColumn

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel(),
    isActivation: Boolean = false,
    onDetailStock: (String) -> Unit,
) {
    val marketRecommendation by viewModel.currentMarketRecommendation.collectAsStateWithLifecycle()
    val marketPopular by viewModel.currentMarketPopular.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCurrentMarketRecommendation()
        viewModel.getCurrentMarketPopular()
    }

    val rememberLazyState = rememberLazyListState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){

        item(span = { GridItemSpan(currentLineSpan = maxLineSpan)}) {
            Row() {
                Text("Markets")
                Icon(imageVector = Icons.Filled.Search, contentDescription = "")
            }
        }

        item(span = { GridItemSpan(currentLineSpan = maxLineSpan)}) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.popCategories.forEachIndexed { index, category ->
                    FilterButton(
                        text = category,
                        isSelected = viewModel.currentMarketSelectable.value == category,
                        onClick = {
                            viewModel.selectMarketPop(index = index)
                        }
                    )
                }
            }
        }

        item(span = { GridItemSpan(currentLineSpan = maxLineSpan)}) {
            val marketPopular by viewModel.currentMarketSelectable.collectAsStateWithLifecycle()
            Text(text = marketPopular)
        }

        when(marketPopular){
            is StateUI.Data -> {
                val currentData = (marketPopular as StateUI.Data<List<MarketPopularModel.SmallQuote>>).value
                items(count = currentData.size){ i ->
                    CardOverview(data = currentData[i]){ symbol ->
                        onDetailStock(symbol)
                    }
                }
            }
            is StateUI.Error -> {

            }
            is StateUI.Loading-> {}
        }

        item(span = { GridItemSpan(currentLineSpan = maxLineSpan)}) {
            /*HR*/
        }

        when(marketRecommendation){
            is StateUI.Data -> {
                val currentData = (marketRecommendation as StateUI.Data<List<Recommendation.Quotes>>).value.map {
                    it.asHomeDailySummary()
                }
                items(count = currentData.size){ i ->
                }
            }
            is StateUI.Error -> {

            }
            is StateUI.Loading-> {

            }
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