package dev.daniza.portfoliowatcher.interactor.get_home_daily_summary

import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import javax.inject.Inject

class GetHomeDailySummaryInteractor @Inject constructor(
    private val homeSummaryRepository: HomeSummaryRepository
) {
    suspend operator fun invoke(
        token: String,
        symbols: List<String>
    ) = homeSummaryRepository.getHomeDailySummaryData(token, symbols)
}