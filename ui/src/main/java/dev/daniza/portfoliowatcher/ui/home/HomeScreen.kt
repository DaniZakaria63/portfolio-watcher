package dev.daniza.portfoliowatcher.ui.home

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.daniza.portfoliowatcher.ui.component.LineChartCompose
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToDetail: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        LazyColumn {
            item {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
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
            item {
                HorizontalDivider(thickness = 1.dp)
            }
            item {
                Text("This should be favorite list")
                LazyRow {
                    item { LineChartCompose(modifier = Modifier
                        .height(40.dp)
                        .width(90.dp)) }
                }
            }
            item {
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(10.dp),
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
                                values = listOf(150.0, 152.0, 148.0, 155.0, 160.0, 158.0, 162.0, 165.0, 170.0, 175.0),
                                color = SolidColor(Color.Blue),
                                firstGradientFillColor = Color.Blue.copy(alpha = 0.3f),
                                secondGradientFillColor = Color.Transparent,
                                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                                gradientAnimationDelay = 1000,
                                drawStyle = DrawStyle.Stroke(1.dp)
                            )
                        )
                    },
                    animationMode = AnimationMode.Together(delayBuilder = {
                        it * 500L
                    })
                )
            }
        }
    }
}