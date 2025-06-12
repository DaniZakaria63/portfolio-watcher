package dev.daniza.portfoliowatcher.ui.home

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.daniza.portfoliowatcher.ui.component.FavoriteHomeCard
import dev.daniza.portfoliowatcher.ui.model.FavoriteHomeUIModel
import dev.daniza.portfoliowatcher.ui.model.sampleFavoriteHomeUIModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToDetail: (String) -> Unit) {
    val favoriteListState = rememberLazyListState()
    val sampleSelectedChartData = remember {
        FavoriteHomeUIModel(
            id = "sample",
            name = "Sample",
            price = "$1000",
            changePercent = "+5%",
            fullChartData = listOf<Double>(30.0, 100.0, 148.0, 155.0, 74.0, 120.0, 74.0, 50.0, 10.0, 40.0)
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        Column {
            HomeFavoriteSection()
            HorizontalDivider(thickness = 1.dp)

            LazyRow(state = favoriteListState) {
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

            HomeChartSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp),
                model = sampleSelectedChartData
            )
            HorizontalDivider(thickness = 1.dp)

            LazyColumn {
                items(3) {
                    PopularCoinSection()
                }
            }
        }
    }
}

@Composable
fun PopularCoinSection() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ethereum",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE6F3F3),
                ) {
                    Text(
                        text = "ETH",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color(0xFF2E8B83),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "$2,800.00",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "+3.5%",
                    color = Color.Green,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun HomeChartSection(
    modifier: Modifier = Modifier,
    model: FavoriteHomeUIModel
) {
    Column {
        Text(
            text = model.name,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp),
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Left
        )
        Row {
            Text(
                text = model.price,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp),
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Left
            )
            Text(
                text = model.changePercent,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp),
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Left
            )
        }
        LineChart(
            modifier = modifier,
            gridProperties = GridProperties(
                enabled = false,
                GridProperties.AxisProperties(
                    enabled = false,
                    thickness = 0.dp,
                    color = SolidColor(Color.Transparent),
                ),
                GridProperties.AxisProperties(
                    enabled = false,
                    thickness = 0.dp,
                    color = SolidColor(Color.Transparent)
                ),
            ),
            dotsProperties = DotProperties(enabled = false),
            dividerProperties = DividerProperties(enabled = false),
            labelHelperProperties = LabelHelperProperties(enabled = false),
            labelHelperPadding = 0.dp,
            indicatorProperties = HorizontalIndicatorProperties(enabled = false),
            labelProperties = LabelProperties(enabled = false),
            zeroLineProperties = ZeroLineProperties(enabled = false, thickness = 0.dp),
            curvedEdges = false,
            data = remember {
                listOf(
                    Line(
                        label = "AAPL",
                        values = model.fullChartData,
                        color = SolidColor(Color.Blue),
                        firstGradientFillColor = Color.Blue.copy(alpha = 0.3f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(1.dp),
                        curvedEdges = true
                    )
                )
            },
            animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            })
        )
        Text(
            text = model.timeframe.toString(),
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp),
            color = Color.Gray,
            fontSize = 12.sp,
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun HomeFavoriteSection() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp, top = 12.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.padding(start = 7.dp, top = 4.dp, bottom = 4.dp),
                tint = Color.Black
            )
            Text(
                text = "Search",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                color = Color.Black,
                fontSize = 13.sp,
                textAlign = TextAlign.Left
            )
        }
    }
}