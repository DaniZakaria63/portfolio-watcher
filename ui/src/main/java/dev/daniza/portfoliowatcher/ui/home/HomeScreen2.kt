package dev.daniza.portfoliowatcher.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.daniza.portfoliowatcher.ui.component.GoldLabel
import dev.daniza.portfoliowatcher.ui.component.Instrument
import dev.daniza.portfoliowatcher.ui.component.InstrumentListItem
import dev.daniza.portfoliowatcher.ui.component.MarketTickerRow
import dev.daniza.portfoliowatcher.ui.component.SearchBar
import dev.daniza.portfoliowatcher.ui.component.SearchDialog
import dev.daniza.portfoliowatcher.ui.component.dummyTickerItems

@Composable
fun HomeScreen2(
    onNavigateToDetail: (String) -> Unit
) {
    var showSearchDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            onSearchClick = { showSearchDialog = true},
            activeInstrument = "GOLD",
        )

        MarketTickerRow(tickerItems = dummyTickerItems)

        PortfolioSummary()

        ListsSection()
    }

    if(showSearchDialog) {
        SearchDialog(
            onDismiss = { showSearchDialog = false },
            onInstrumentSelected = { instrument ->
                // Handle selection, e.g., update active instrument
                showSearchDialog = false
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
                Divider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // 2.4 Instrument List Items
                InstrumentListItem(
                    symbol = "AAPL",
                    companyName = "Apple Inc.",
                    chartData = listOf(235.0f, 234.8f, 234.6f, 234.5f, 234.4f, 234.3f, 234.65f),
                    currentPrice = "234.65",
                    dailyChangePercent = -0.46f // Negative for loss
                )

                Divider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                InstrumentListItem(
                    symbol = "CRWD",
                    companyName = "CrowdStrike Hol...",
                    chartData = listOf(361.0f, 360.8f, 360.6f, 360.5f, 360.4f, 360.3f, 360.56f),
                    currentPrice = "360.56",
                    dailyChangePercent = -1.34f // Negative for loss
                )

                Divider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                InstrumentListItem(
                    symbol = "BABA",
                    companyName = "Alibaba Group H...",
                    chartData = listOf(140.0f, 139.8f, 139.6f, 139.5f, 139.4f, 139.3f, 139.15f),
                    currentPrice = "139.15",
                    dailyChangePercent = -1.33f // Negative for loss
                )
            }
        }
    }
}