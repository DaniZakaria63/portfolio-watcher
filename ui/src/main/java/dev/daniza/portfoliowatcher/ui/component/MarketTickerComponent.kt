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
import androidx.compose.material3.HorizontalDivider
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
import dev.daniza.portfoliowatcher.model.parser.orZero
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel

@Composable
fun MarketTickerRow(tickerItems: List<HomeDailySummaryModel>) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        tickerItems.forEachIndexed { index, item ->
            TickerItemColumn(item = item)

            if (index < tickerItems.size - 1) {
                HorizontalDivider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .width(1.dp)
                        .height(60.dp)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun TickerItemColumn(item: HomeDailySummaryModel) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SmallLineChart(
            data = item.candles.orEmpty().map { it.closePrice.orZero() },
            lineColor = Color.Green,
            baselineColor = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.name.orEmpty().ifEmpty { "-" },
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "${item.currentPrice}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

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
                text = "${if (item.gain.orZero() >= 0) "+" else ""}${item.gain}%",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SmallLineChart(data: List<Double>, lineColor: Color, baselineColor: Color) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        if (data.size < 2) return@Canvas

        val width = size.width
        val height= size.height

        val globalMin: Double = (data.minOrNull() ?: 0).toDouble()
        val globalMax: Double = (data.maxOrNull() ?: 1).toDouble()
        val range = globalMax - globalMin

        val path = Path().apply {
            moveTo(0f, height)
            lineTo(width, height)
        }
        drawPath(
            path = path,
            color = baselineColor,
            style = Stroke(
                width = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
            )
        )

        val linePath = Path().apply {
            val step = width / (data.size - 1)
            moveTo(0f, height - ((data[0] - globalMin).toFloat() / range.toFloat() * height))
            for (i in 1 until data.size) {
                val x = i * step
                val y = height - ((data[i] - globalMin).toFloat() / range.toFloat() * height)
                lineTo(x, y)
            }
        }

        drawPath(linePath, color = lineColor, style = Stroke(width = 2.dp.toPx()))
    }
}