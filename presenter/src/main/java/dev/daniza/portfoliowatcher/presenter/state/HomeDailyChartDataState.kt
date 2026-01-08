package dev.daniza.portfoliowatcher.presenter.state

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel

class HomeDailyChartDataState(
    val symbol: String? = null,
    val range: RangeDate? = null,
    val data: HomeDailyChartModel? = null,
) {
    sealed class RangeDate(val param: String) {
        object M15 : RangeDate("15m")
        object H1 : RangeDate("1h")
        object D1 : RangeDate("1d")
        object W1 : RangeDate("1wk")
        object Mo1 : RangeDate("1mo")
    }
}