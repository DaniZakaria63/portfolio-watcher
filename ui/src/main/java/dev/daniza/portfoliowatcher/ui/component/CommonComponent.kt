package dev.daniza.portfoliowatcher.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.daniza.portfoliowatcher.ui.model.FavoriteHomeUIModel
import dev.daniza.portfoliowatcher.ui.model.sampleFavoriteHomeUIModel
import dev.daniza.portfoliowatcher.ui.theme.PortfolioWatcherTheme


@Composable
fun DotHorizontalLine(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    height: Dp = 40.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        drawLine(
            color = color,
            start = Offset(0.dp.toPx(), 10.dp.toPx()),
            end = Offset(x = size.width - 0.dp.toPx(), 10.dp.toPx()),
            strokeWidth = 6f,
            cap = StrokeCap.Square,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(0f, 8.dp.toPx())
            )
        )
    }
}

@Preview
@Composable
fun DotHorizontalLinePreview() {
    PortfolioWatcherTheme {
        DotHorizontalLine()
    }
}

@Composable
fun FavoriteHomeCard(
    modifier: Modifier = Modifier,
    model: FavoriteHomeUIModel
) {
    Box() {
        Column(modifier = modifier) {
            DotHorizontalLine(
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.Black,
                height = 8.dp
            )
            LineChartCompose(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(30.dp)
                    .width(60.dp),
                list = model.chartData,
            )
            Text(
                model.name,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
            )
            Text(
                model.price,
                color = Color.Black,
                modifier = Modifier.padding(top = 0.5.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Box(
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.8f), shape = RoundedCornerShape(4.dp))
                    .padding(start = 8.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
            ) {
                Text(
                    model.changePercent,
                    color = Color.White,
                    modifier = Modifier.padding(top = 0.5.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

    }
}

@Preview
@Composable
fun FavoriteHomeCardPreview() {
    PortfolioWatcherTheme {
        LazyRow {
            itemsIndexed(sampleFavoriteHomeUIModel) { index, item ->
                Row {
                    FavoriteHomeCard(
                        modifier = Modifier
                            .width(92.dp)
                            .padding(horizontal = 12.dp),
                        model = item
                    )
                    if (index < sampleFavoriteHomeUIModel.size - 1) {
                        VerticalDivider(
                            thickness = 1.dp,
                            modifier = Modifier.height(120.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AvatarCircle(symbol: String) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol.take(4).uppercase(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun AvatarCirclePreview(){
    AvatarCircle("AAPL")
}