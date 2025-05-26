package dev.daniza.portfoliowatcher.ui.component

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tradingview.lightweightcharts.api.chart.models.color.surface.SolidColor
import com.tradingview.lightweightcharts.api.chart.models.color.toIntColor
import com.tradingview.lightweightcharts.api.options.models.*
import com.tradingview.lightweightcharts.api.series.enums.CrosshairMode
import com.tradingview.lightweightcharts.api.series.enums.LineStyle
import com.tradingview.lightweightcharts.api.series.models.HistogramData
import com.tradingview.lightweightcharts.runtime.plugins.DateTimeFormat
import com.tradingview.lightweightcharts.runtime.plugins.PriceFormatter
import com.tradingview.lightweightcharts.runtime.plugins.TimeFormatter
import com.tradingview.lightweightcharts.view.ChartsView


@Composable
fun TradingViewChartCompose(
    modifier: Modifier,
    chartOptions: ChartOptions,
    initialChartData: List<HistogramData>,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            ChartsView(context).apply {
                api.applyOptions {
                    layout = layoutOptions {
                        background = SolidColor(Color.BLACK.toIntColor())
                        textColor = Color.WHITE.toIntColor()
                    }
                    localization = localizationOptions {
                        locale = "id-ID"
                        priceFormatter = PriceFormatter(template = "IDR{price:#2:#3}")
                        timeFormatter = TimeFormatter(
                            locale = "id-ID",
                            dateTimeFormat = DateTimeFormat.DATE_TIME
                        )
                    }
                    grid = gridOptions {
                        vertLines = gridLineOptions { visible = false }
                    }
                    timeScale = timeScaleOptions {
                        visible = true
                        borderVisible = false
                    }
                    crosshair = crosshairOptions {
                        mode = CrosshairMode.MAGNET
                        vertLine = CrosshairLineOptions(style = LineStyle.DASHED)
                        horzLine = CrosshairLineOptions(
                            visible = true,
                            style = LineStyle.DASHED,
                            labelVisible = true,
                            labelBackgroundColor = Color.BLACK.toIntColor()
                        )
                    }
                }
                api.addHistogramSeries(onSeriesCreated = { seriesApi ->
                    seriesApi.setData(initialChartData)
                })
            }
        },
        update = { chartsView ->
            chartsView.api.applyOptions {
                layout = layoutOptions {
                    background = SolidColor(Color.BLACK.toIntColor())
                    textColor = Color.WHITE.toIntColor()
                }
            }
        }
    )
}