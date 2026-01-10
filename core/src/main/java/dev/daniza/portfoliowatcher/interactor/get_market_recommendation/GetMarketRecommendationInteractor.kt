package dev.daniza.portfoliowatcher.interactor.get_market_recommendation

import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import javax.inject.Inject

class GetMarketRecommendationInteractor @Inject constructor(
    private val homeSummaryRepository: HomeSummaryRepository
) {
    suspend operator fun invoke() = homeSummaryRepository.getMarketRecommendation()
}