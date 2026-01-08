 /*
  * Portfolio Watcher - SearchBarFixed.kt
  *
  * Main Author: Dani Zakaria
  * Email: dani.zakaria@proton.me
  * GitHub: @danizakaria63
  * Created: November 2025
  * Last Modified: November 19, 2025
  *
  * Description: Search components for the portfolio watcher app including search bar, search dialog, and instrument items
  */

package dev.daniza.portfoliowatcher.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Search bar component that displays a clickable search field
 *
 * @param onSearchClick Callback when the search bar is clicked to open the search dialog
 */
@Composable
fun SearchBar(onSearchClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clickable { onSearchClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Search",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

/**
 * Gold label component that displays the active instrument with a yellow background
 *
 * @param activeInstrument The currently active instrument symbol (e.g., "GOLD")
 */
@Composable
fun GoldLabel(activeInstrument: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Yellow)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = activeInstrument,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

/**
 * Search dialog component that displays a bottom sheet with search functionality
 *
 * @param onDismiss Callback to dismiss the dialog
 * @param onInstrumentSelected Callback when an instrument is selected
 * @param initialInstruments List of instruments to display in the search
 */
@Composable
fun SearchDialog(
    onDismiss: () -> Unit,
    onInstrumentSelected: (Instrument) -> Unit,
    initialInstruments: List<Instrument> = emptyList()
) {
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category.ALL) }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded
    )

    LaunchedEffect(Unit) {
        bottomSheetState.show()
    }

    LaunchedEffect(bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            onDismiss() // Ensure onDismiss is called when sheet is hidden
        }
    }

    val filteredInstruments = remember(searchText, selectedCategory, initialInstruments) {
        val baseList = when (selectedCategory) {
            Category.ALL -> initialInstruments
            Category.EQUITIES -> initialInstruments.filter { it.type == "Equity" }
            Category.FUTURES -> initialInstruments.filter { it.type == "Futures" }
        }
        if (searchText.isBlank()) {
            baseList
        } else {
            baseList.filter { it.symbol.contains(searchText, ignoreCase = true) || it.name.contains(searchText, ignoreCase = true) }
        }
    }

    val currentHeight = LocalConfiguration.current
    val sheetHeight = currentHeight.screenHeightDp.dp * 0.9f
    val sheetHeightState by remember { mutableStateOf(sheetHeight) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeightState) // Fill the Box's height
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular Today",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    TextButton(onClick = {
                        onDismiss()
                    }) {
                        Text("Done")
                    }
                }

                // Category Tabs
                CategoryTabs(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                // Search Field
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    placeholder = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    singleLine = true
                )

                // Instrument List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(filteredInstruments.size) { index ->
                        val instrument = filteredInstruments[index]
                        InstrumentItem(
                            instrument = instrument,
                            onFavoriteClick = { /* Handle favorite toggle */ },
                            onItemSelected = {
                                onInstrumentSelected(instrument)
                                scope.launch { bottomSheetState.hide() }
                            }
                        )
                    }
                }

                // See More Link
                TextButton(onClick = { /* Load more instruments */ }) {
                    Text("See more...")
                }
            }
        },
        content = {},
    )
}

/**
 * Category tabs component for filtering instruments by type
 *
 * @param selectedCategory The currently selected category
 * @param onCategorySelected Callback when a category is selected
 */
@Composable
fun CategoryTabs(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryTab(
            text = "All",
            isSelected = selectedCategory == Category.ALL,
            onClick = { onCategorySelected(Category.ALL) }
        )
        CategoryTab(
            text = "Equities",
            isSelected = selectedCategory == Category.EQUITIES,
            onClick = { onCategorySelected(Category.EQUITIES) }
        )
        CategoryTab(
            text = "Futures",
            isSelected = selectedCategory == Category.FUTURES,
            onClick = { onCategorySelected(Category.FUTURES) }
        )
    }
}

/**
 * Individual category tab component
 *
 * @param text The text to display on the tab
 * @param isSelected Whether this tab is currently selected
 * @param onClick Callback when the tab is clicked
 */
@Composable
fun CategoryTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
        color = if (isSelected) Color.White else Color.Black,
        modifier = Modifier
            .background(
                color = if (isSelected) Color.DarkGray else Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    )
}

/**
 * Individual instrument item component for displaying search results
 *
 * @param instrument The instrument data to display
 * @param onFavoriteClick Callback when the favorite star is clicked
 * @param onItemSelected Callback when the instrument item is clicked
 */
@Composable
fun InstrumentItem(
    instrument: Instrument,
    onFavoriteClick: () -> Unit,
    onItemSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemSelected)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Symbol Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = instrument.symbol.take(3), // Assuming symbols are short
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Name and Type
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${instrument.symbol} ${instrument.type}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = instrument.symbol,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Price and Change
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = instrument.price.formatAsCurrency(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "${if (instrument.changePercent >= 0) "+" else ""}${instrument.changePercent}%",
                style = MaterialTheme.typography.bodySmall,
                color = if (instrument.changePercent >= 0) Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Favorite Star
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (instrument.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Favorite"
            )
        }
    }
}

// Extension function for formatting currency
fun Double.formatAsCurrency(): String {
    return "$%.2f".format(this)
}

/**
 * Data class representing a financial instrument
 *
 * @param symbol The instrument symbol (e.g., "CRWD")
 * @param name The full name of the instrument (e.g., "CrowdStrike Holdings")
 * @param type The type of instrument ("Equity", "Futures", etc.)
 * @param price The current price of the instrument
 * @param changePercent The percentage change in price
 * @param isFavorite Whether the instrument is marked as a favorite
 */
data class Instrument(
    val symbol: String,
    val name: String,
    val type: String,
    val price: Double,
    val changePercent: Double,
    val isFavorite: Boolean = false
)

/**
 * Enum representing different categories of instruments that can be filtered
 */
enum class Category {
    ALL, EQUITIES, FUTURES
}
