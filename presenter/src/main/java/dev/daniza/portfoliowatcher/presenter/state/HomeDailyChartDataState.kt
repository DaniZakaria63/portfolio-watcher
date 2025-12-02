package dev.daniza.portfoliowatcher.presenter.state

import dev.daniza.portfoliowatcher.model.selfhost.HomeDailyChartModel

class HomeDailyChartDataState(
    val symbol: String? = null,
    val range: RangeDate? = null,
    val data: HomeDailyChartModel? = null,
) {
    sealed class RangeDate(val param: String) {
        object DAILY : RangeDate("TIME_SERIES_DAILY")
        object WEEKLY : RangeDate("TIME_SERIES_WEEKLY")
        object MONTHLY : RangeDate("TIME_SERIES_MONTHLY")
    }
}