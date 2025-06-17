package dev.daniza.portfoliowatcher.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.daniza.portfoliowatcher.local.dao.NewsDao
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import dev.daniza.portfoliowatcher.pager.NewsRemoteMediator
import dev.daniza.portfoliowatcher.remote.news.NewsRemote
import dev.daniza.portfoliowatcher.remote.service.ConnectivityChecker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    val newsRemote: NewsRemote,
    val newsDao: NewsDao,
    val connectivityChecker: ConnectivityChecker
) : NewsRepository {
    override suspend fun getNewsHeadline(): Flow<PagingData<NewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 7,
                enablePlaceholders = true,
                initialLoadSize = 30
            ),
            remoteMediator = NewsRemoteMediator(
                newsDao = newsDao,
                newsRemoteService = newsRemote,
                connectivityChecker = connectivityChecker
            ),
            pagingSourceFactory = { newsDao.getAllNews() }
        ).flow
    }
}