package dev.daniza.portfoliowatcher.interactor.get_news

import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import dev.daniza.portfoliowatcher.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsInteractor @Inject constructor(
    val repository: NewsRepository,
) {
    suspend operator fun invoke(): Flow<PagingData<NewsEntity>> = repository.getNewsHeadline()
}