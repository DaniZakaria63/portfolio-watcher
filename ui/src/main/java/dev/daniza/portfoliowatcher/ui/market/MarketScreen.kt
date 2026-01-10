package dev.daniza.portfoliowatcher.ui.market

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.itemKey
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.selfhost.Recommendation
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.MarketViewModel

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = hiltViewModel(),
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
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Search") },
        modifier = modifier,
        singleLine = true,
        keyboardActions = KeyboardActions {
            onSearch()
        },
    )
}