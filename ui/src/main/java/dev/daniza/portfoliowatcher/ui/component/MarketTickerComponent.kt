package dev.daniza.portfoliowatcher.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.daniza.portfoliowatcher.model.parser.orZero
import dev.daniza.portfoliowatcher.model.selfhost.HomeDailySummaryModel
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel
import dev.daniza.portfoliowatcher.model.selfhost.MarketPopularModel.SmallQuote


@Composable
fun TickerItemColumn(
    item: HomeDailySummaryModel,
    onTickerClicked: (symbol: HomeDailySummaryModel) -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .clickable(interactionSource = remember { MutableInteractionSource() }) {
                onTickerClicked(item)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SmallLineChart(
            data = item.candles.orEmpty().map { it.orZero() },
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
        val height = size.height

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

@Composable
fun CardOverview(
    data: SmallQuote,
    onClicked: (symbol: String)-> Unit
) {
    OutlinedCard(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(0.3.dp, MaterialTheme.colorScheme.onSurface),
        modifier = Modifier.defaultMinSize(minWidth = 120.dp).padding(4.dp),
        onClick = { onClicked(data.symbol) }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            Text(
                text = data.symbol,
                fontSize = 12.sp,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = data.price.formatAsCurrency(),
                fontSize = 8.sp,
                style = MaterialTheme.typography.labelSmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star icon",
                    tint = Color.Green,
                    modifier = Modifier.padding(end = 2.dp).size(16.dp)
                )
                Text(
                    text = data.changePercent.toString(),
                    fontSize = 8.sp,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(size = 2.dp)
                    ).background(color = Color.Green).padding(vertical = 2.dp, horizontal = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardOverviewPreview() {
    CardOverview(
        data = MarketPopularModel.SmallQuote(
            type = "Equity",
            changePercent = -1.588777,
            change = -2.9100037,
            price = 180.25,
            symbol = "NVDA",
            name = "NVIDIA",
            shortName = "NVIDIA Corporation"
        )
    ){

    }
}