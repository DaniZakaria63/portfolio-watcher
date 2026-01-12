package dev.daniza.portfoliowatcher.interactor.get_market_search

import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import javax.inject.Inject

class GetMarketSearchInteractor @Inject constructor(
    private val homeSummaryRepository: HomeSummaryRepository
) {
    suspend operator fun invoke(query: String) = homeSummaryRepository.getSearchStock(query)
}