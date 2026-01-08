package dev.daniza.portfoliowatcher.interactor.get_home_recommendation

import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import javax.inject.Inject

class GetHomeRecommendationInteractor @Inject constructor(
    private val homeSummaryRepository: HomeSummaryRepository
) {
    suspend operator fun invoke() = homeSummaryRepository.getHomeRecommendation()
}