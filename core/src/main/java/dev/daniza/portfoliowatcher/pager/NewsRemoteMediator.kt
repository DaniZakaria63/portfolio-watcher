package dev.daniza.portfoliowatcher.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.daniza.portfoliowatcher.local.dao.NewsDao
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import dev.daniza.portfoliowatcher.parser.toEntity
import dev.daniza.portfoliowatcher.remote.news.NewsRemote
import dev.daniza.portfoliowatcher.remote.service.ConnectivityChecker
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsDao: NewsDao,
    private val newsRemoteService: NewsRemote,
    private val connectivityChecker: ConnectivityChecker
) : RemoteMediator<Int, NewsEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val isConnected = connectivityChecker.isNetworkAvailable()

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    newsDao.deleteAll()
                    1
                }

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (lastItem.id / state.config.pageSize) + 1
                }
            }

            if (!isConnected) {
                return MediatorResult.Error(IOException("No internet connection"))
            }

            val newsHeadlines = newsRemoteService.getNewsHeadline(
                page = loadKey.toInt(),
                pageSize = state.config.pageSize
            )

            newsDao.insertAll(newsHeadlines.map { it.toEntity() })

            MediatorResult.Success(
                endOfPaginationReached = newsHeadlines.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}