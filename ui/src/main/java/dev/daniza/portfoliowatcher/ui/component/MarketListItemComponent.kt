package dev.daniza.portfoliowatcher.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

@Composable
fun InstrumentListItem(
    symbol: String,
    companyName: String,
    chartData: List<Double>,
    currentPrice: String,
    dailyChangePercent: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), // Fixed height to match visual compactness
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. LEFT COLUMN: Symbol and Company Name
        Column(
            modifier = Modifier.weight(1f), // Take available space
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = companyName,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // 2. MIDDLE COLUMN: Small Line Chart
        // Use a fixed width for the chart to ensure consistent sizing
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(30.dp) // Fixed height for the chart area
        ) {
            SmallLineChart(
                data = chartData,
                lineColor = Color.Red,
                baselineColor = Color.Gray
            )
        }

        // 3. RIGHT COLUMN: Price and Daily Change Badge (Stacked)
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp) // Small space between price and badge
        ) {
            // Current Price (Plain text, no label, bold)
            Text(
                text = currentPrice,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            // Daily Change Badge (Colored background)
            Box(
                modifier = Modifier
                    .background(
                        color = if (dailyChangePercent >= 0) Color.Green else Color.Red,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${if (dailyChangePercent >= 0) "+" else ""}${dailyChangePercent}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}