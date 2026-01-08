package dev.daniza.portfoliowatcher.interactor.process_small_stock_db

import dev.daniza.portfoliowatcher.local.entity.SmallStockEntity
import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import javax.inject.Inject

class ProcessSmallStockInteractor @Inject constructor(
    private val homeSummaryRepository: HomeSummaryRepository
) {
    suspend operator fun invoke(action: Int, data: SmallStockEntity?) =
        homeSummaryRepository.processTheStock(action, data)
}