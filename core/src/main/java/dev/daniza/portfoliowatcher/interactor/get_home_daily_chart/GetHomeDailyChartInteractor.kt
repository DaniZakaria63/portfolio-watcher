package dev.daniza.portfoliowatcher.interactor.get_home_daily_chart

import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import javax.inject.Inject

class GetHomeDailyChartInteractor @Inject constructor(
    private val homeSummaryRepository: HomeSummaryRepository
) {
    suspend operator fun invoke(
        token: String,
        symbol: String,
        range: String
    ) =  homeSummaryRepository.getHomeDailyChartData(token, symbol, range)
}