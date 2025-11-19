package dev.daniza.portfoliowatcher.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.daniza.portfoliowatcher.ui.model.TickerItem

@Composable
fun MarketTickerRow(tickerItems: List<TickerItem>) {
    // Use HorizontalScrollState for horizontal scrolling
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp) // No space, use divider for separation
    ) {
        tickerItems.forEachIndexed { index, item ->
            TickerItemColumn(item = item)

            // Add a vertical divider after each item except the last one
            if (index < tickerItems.size - 1) {
                Divider(
                    color = Color.LightGray, // Or a specific gray from your design system
                    modifier = Modifier
                        .width(1.dp)
                        .height(60.dp) // Adjust height to match the content height
                        .padding(vertical = 8.dp) // Optional padding around divider
                )
            }
        }
    }
}

@Composable
fun TickerItemColumn(item: TickerItem) {
    Column(
        modifier = Modifier
            .width(100.dp) // Fixed width based on visual estimation from image
            .padding(8.dp), // Padding inside the column
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp) // Space between elements
    ) {
        // 1. Small Line Chart (Green line with dotted baseline)
        SmallLineChart(
            data = item.chartData,
            isPositive = item.isPositive,
            lineColor = Color.Green, // Green line as seen in image
            baselineColor = Color.Gray // Dotted baseline
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 2. Instrument Name
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))

        // 3. Current Value
        Text(
            text = item.value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 4. Percentage Change Badge (Green background)
        Box(
            modifier = Modifier
                .background(
                    color = Color.Green,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 4.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${if (item.changePercent >= 0) "+" else ""}${item.changePercent}%",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ChangePercentText(percent: Float, isPositive: Boolean) {
    val color = if (isPositive) Color.Green else Color.Red
    Text(
        text = "${if (isPositive) "+" else ""}${percent}%",
        style = MaterialTheme.typography.bodySmall,
        color = color,
        modifier = Modifier
            .background(
                color = if (isPositive) Color.Green.copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}
@Composable
fun SmallLineChart(data: List<Float>, isPositive: Boolean, lineColor: Color, baselineColor: Color) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp) // Adjust height to match image
    ) {
        if (data.size < 2) return@Canvas

        val width = size.width
        val height = size.height

        // Calculate global min/max for consistent scaling
        val globalMin = data.minOrNull() ?: 0f
        val globalMax = data.maxOrNull() ?: 1f
        val range = globalMax - globalMin

        // Draw the dotted baseline (at the bottom of the canvas)
        val path = Path().apply {
            moveTo(0f, height)
            lineTo(width, height)
        }
        drawPath(
            path = path,
            color = baselineColor,
            style = Stroke(
                width = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f) // Dotted line
            )
        )

        // Draw the actual line
        val linePath = Path().apply {
            val step = width / (data.size - 1)
            moveTo(0f, height - ((data[0] - globalMin) / range * height))
            for (i in 1 until data.size) {
                val x = i * step
                val y = height - ((data[i] - globalMin) / range * height)
                lineTo(x, y)
            }
        }

        drawPath(linePath, color = lineColor, style = Stroke(width = 2.dp.toPx()))
    }
}

val dummyTickerItems = listOf(
    TickerItem(
        name = "Crude Oil",
        value = "74.25",
        changePercent = 1.2f,
        isPositive = true, // Assuming current price > opening price
        chartData = listOf(72.1f, 72.8f, 73.0f, 73.5f, 73.8f, 74.0f, 74.25f),
        openingPrice = 73.0f
    ),
    TickerItem(
        name = "Gold",
        value = "2025.50",
        changePercent = -0.3f,
        isPositive = false, // Assuming current price < opening price
        chartData = listOf(2030.0f, 2028.5f, 2027.0f, 2026.0f, 2025.8f, 2025.6f, 2025.5f),
        openingPrice = 2026.0f
    ),
    TickerItem(
        name = "Silver",
        value = "23.10",
        changePercent = 0.8f,
        isPositive = true, // Assuming current price > opening price
        chartData = listOf(22.8f, 22.9f, 23.0f, 23.05f, 23.08f, 23.09f, 23.10f),
        openingPrice = 23.0f
    ),
    TickerItem(
        name = "EUR/USD",
        value = "1.0850",
        changePercent = -0.1f,
        isPositive = false, // Assuming current price < opening price
        chartData = listOf(1.0860f, 1.0858f, 1.0855f, 1.0852f, 1.0851f, 1.0850f, 1.0850f),
        openingPrice = 1.0851f
    )
)