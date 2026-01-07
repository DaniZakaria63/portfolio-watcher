package dev.daniza.portfoliowatcher.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.daniza.portfoliowatcher.model.formatCurrency
import dev.daniza.portfoliowatcher.model.formatPercent
import dev.daniza.portfoliowatcher.model.orDash
import dev.daniza.portfoliowatcher.model.orZero
import dev.daniza.portfoliowatcher.model.selfhost.Recommendation
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.HomeViewModel
import dev.daniza.portfoliowatcher.ui.component.AvatarCircle
import dev.daniza.portfoliowatcher.ui.component.GoldLabel
import dev.daniza.portfoliowatcher.ui.component.Instrument
import dev.daniza.portfoliowatcher.ui.component.InstrumentListItem
import dev.daniza.portfoliowatcher.ui.component.MarketTickerRow
import dev.daniza.portfoliowatcher.ui.component.SearchBar
import dev.daniza.portfoliowatcher.ui.component.SearchDialog
import dev.daniza.portfoliowatcher.ui.model.FavoriteHomeUIModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
) {
    val dailySummaryChart by viewModel.currentDailySummaryState.collectAsStateWithLifecycle()
    var showSearchDialog by remember { mutableStateOf(false) }
    val sampleSelectedChartData = remember {
        FavoriteHomeUIModel(
            id = "sample",
            name = "Sample",
            price = "$1000",
            changePercent = "+5%",
            fullChartData = listOf(30.0, 100.0, 148.0, 155.0, 74.0, 120.0, 74.0, 50.0, 10.0, 40.0)
        )
    }
    val currentDailyDailyGainLoseState by viewModel.currentDailyDailyGainLoseState.collectAsStateWithLifecycle()

    val rememberLazyListState = rememberLazyListState()
    LazyColumn(
        state = rememberLazyListState,
        modifier = Modifier
        .fillMaxSize()
    ) {
        item {
            TopAppBar(
                onSearchClick = { showSearchDialog = true},
                activeInstrument = "GOLD",
            )
        }

        item {
            /*WE WORK ON THIS FUNCTION*/
            MarketTickerRow(tickerItems = dailySummaryChart)
            /*==========================*/
        }
        /*
        PortfolioSummary()

        HomeChartSection(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            model = sampleSelectedChartData
        )

        CreateFirstWatchlistCard()
*/


        item {
            Text(
                text = "Popular Today",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.categories.forEach { category ->
                    FilterButton(
                        text = category,
                        isSelected = viewModel._currentDailyGainLoseSelectable.value == category,
                        onClick = {
                            viewModel._currentDailyGainLoseSelectable.value = category
                        }
                    )
                }
            }
        }

        when(currentDailyDailyGainLoseState){
            is StateUI.Data -> {
                val justData = (currentDailyDailyGainLoseState as StateUI.Data<List<Recommendation.Quotes>>).value
                itemsIndexed(items = justData, key = { i, q ->
                    q.symbol.orEmpty().ifEmpty { i.toString() }
                }){ index, data ->
                    FinancialListItem(item = data){

                    }
                }
            }
            is StateUI.Error -> {
                item {

                }
            }
            else -> {
                item {

                }
            }
        }

    }

    if(showSearchDialog) {
        SearchDialog(
            onDismiss = { },
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

@Composable
fun TopAppBar(
    onSearchClick: () -> Unit,
    activeInstrument: String = "GOLD"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp), // Fixed height for consistency
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SearchBar(onSearchClick = onSearchClick)
        GoldLabel(activeInstrument = activeInstrument)
    }
}

@Composable
fun PortfolioSummary() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 1. Top Row: "All Holdings" Button and "Expand Chart" Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "All Holdings" Button (Rounded shape)
            Button(
                onClick = { /* Handle dropdown */ },
                shape = RoundedCornerShape(20.dp), // Rounded edges as seen in image
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // White background
                    contentColor = Color.Black // Black text
                ),
                modifier = Modifier.height(40.dp) // Adjust height if needed
            ) {
                Text(text = "All Holdings")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ExpandMore, // Downward arrow
                    contentDescription = "Show options"
                )
            }

            // "Expand Chart" Text (Right-aligned)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ExpandLess, // Upward arrow (or ExpandMore for consistency)
                    contentDescription = "Expand Chart",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Expand Chart",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }

        // 2. Balance Amount (Large, Bold)
        Text(
            text = "$5,865.75", // From the image
            style = MaterialTheme.typography.headlineLarge, // Use largest headline style available
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // 3. Gains Rows (Day's Gain and Total Gain)
        // Day's Gain
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Start, // Align text to the left
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "-27.75 (-0.47%)", // From the image, red for loss
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red, // Red for negative gain
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between value and label
            Text(
                text = "Day's Gain", // Label from image
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

        // Total Gain
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Start, // Align text to the left
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "-27.75 (-0.47%)", // From the image, red for loss
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red, // Red for negative gain
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between value and label
            Text(
                text = "Total Gain", // Label from image
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

        // 4. Last Refreshed (Small text, bottom left)
        Text(
            text = "Last refresh Mar 7, 2025 at 12:35 AM GMT+7", // From the image
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray, // Gray for secondary info
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val backgroundColor = if (isSelected) Color.Black else Color.White
    val textColor = if (isSelected) Color.White else Color.Black
    val border = if (isSelected) BorderStroke(0.dp, Color.Transparent) else BorderStroke(1.dp, Color.LightGray)

    Button(
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        border = border,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        modifier = Modifier.wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun FinancialListItem(
    modifier: Modifier = Modifier,
    item: Recommendation.Quotes,
    onFavoriteToggled: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { /* optional: navigate to detail */ },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AvatarCircle(symbol = item.symbol.orDash())
        Column {
            Text(
                text = item.symbol.orDash(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.quoteType.orDash(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Blue.copy(alpha = 0.7f)
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(0.4f)
        ) {
            Text(
                text = item.regularMarketPrice.formatCurrency(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.regularMarketChangePercent.formatPercent(),
                style = MaterialTheme.typography.bodySmall,
                color = if (item.regularMarketChangePercent.orZero() >= 0) Color.Green else Color.Red
            )
        }

        IconButton(
            onClick = {
                coroutineScope.launch {
                    onFavoriteToggled(item.symbol.orEmpty())
                }
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = if (item.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Favorite",
                tint = if (item.isFavorite) Color.Yellow else Color.Gray
            )
        }
    }
}

@Composable
fun ListsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 1. Header: "Lists"
        Text(
            text = "Lists",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 2. List Card (Transparent with subtle border)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            // Transparent background
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            // Subtle border
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            ),
            // No elevation
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // 2.1 List Header Row: Name, Tag, Value, Gain/Loss
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Name and Tag
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "New list",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.Green.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Enhanced",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Green,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Right: Value and Gain/Loss
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$5,865.75", // Total value
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "-27.75 (-0.47%)", // Gain/Loss
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red, // Red for loss
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Expand/Collapse Icon (Up arrow)
                    Icon(
                        imageVector = Icons.Default.ExpandLess,
                        contentDescription = "Collapse list",
                        tint = Color.Black
                    )
                }

                // 2.2 Action Row: Add Symbol and Edit List
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add symbol",
                            tint = Color.Blue
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add Symbol",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Blue
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit list",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Edit List",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // 2.3 Divider
                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // 2.4 Instrument List Items
                InstrumentListItem(
                    symbol = "AAPL",
                    companyName = "Apple Inc.",
                    chartData = listOf(235.0, 234.8, 234.6, 234.5, 234.4, 234.3, 234.65),
                    currentPrice = "234.65",
                    dailyChangePercent = -0.46f // Negative for loss
                )

                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                InstrumentListItem(
                    symbol = "CRWD",
                    companyName = "CrowdStrike Hol...",
                    chartData = listOf(361.0, 360.8, 360.6, 360.5, 360.4, 360.3, 360.56),
                    currentPrice = "360.56",
                    dailyChangePercent = -1.34f // Negative for loss
                )

                HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                InstrumentListItem(
                    symbol = "BABA",
                    companyName = "Alibaba Group H...",
                    chartData = listOf(140.0, 139.8, 139.6, 139.5, 139.4, 139.3, 139.15),
                    currentPrice = "139.15",
                    dailyChangePercent = -1.33f // Negative for loss
                )
            }
        }
    }
}

@Composable
fun CreateFirstWatchlistCard() {
    // Use Box with a dotted border for the container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)) // Clip to rounded corners
                .drawBehind {
                    // Draw a rounded dotted border
                    val cornerRadius = 12.dp.toPx()
                    val path = Path().apply {
                        addRoundRect(
                            roundRect = RoundRect(
                                left = 0f,
                                top = 0f,
                                right = size.width,
                                bottom = size.height,
                                cornerRadius = CornerRadius(cornerRadius)
                            ),
                            direction = Path.Direction.CounterClockwise
                        )
                    }
                    drawPath(
                        path = path,
                        color = Color.Gray.copy(alpha = 0.7f),
                        style = Stroke(
                            width = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(5f, 5f),
                                phase = 0f,
                            )
                        )
                    )
                }
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Text
            Text(
                text = "Create Your First Watchlist",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), // Smaller than headlineMedium
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtext
            Text(
                text = "Follow companies to receive relevant news, price alerts, and insights.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Add Symbols Button
            Button(
                onClick = { /* Handle add symbols */ },
                shape = RoundedCornerShape(24.dp), // Rounded button
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray, // Dark gray background
                    contentColor = Color.White // White text
                ),
                modifier = Modifier
                    .height(32.dp)
                    .padding(horizontal = 32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add Symbols",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}