package dev.daniza.portfoliowatcher.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel

@Composable
fun SearchScreen(
    tokenItems: LazyPagingItems<TokenSearchModel>,
    modifier: Modifier = Modifier
) {
    var searchQuery = rememberSaveable { "" }
    Surface {
        Column {
            SearchBar(
                modifier = modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { }
            )

            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(
                    count = tokenItems.itemCount,
                    key = tokenItems.itemKey { it.tokenId }
                ) { index ->
                    tokenItems[index]?.let { token ->
                        // Replace with your token item composable
                        Text(text = token.tokenName)
                    }
                }

                when (tokenItems.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Text(text = "Loading more...")
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            val error = tokenItems.loadState.append as LoadState.Error
                            Text(text = "Error: ${error.error.localizedMessage ?: "Unknown error"}")
                        }
                    }

                    else -> {}
                }
            }
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