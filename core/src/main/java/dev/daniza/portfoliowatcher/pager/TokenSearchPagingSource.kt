package dev.daniza.portfoliowatcher.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel
import dev.daniza.portfoliowatcher.remote.service.ConnectivityChecker
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemote
import java.io.IOException

class TokenSearchPagingSource(
    private val query: String,
    private val tokenMetricsService: TokenMetricsRemote,
    private val connectivityChecker: ConnectivityChecker,
) : PagingSource<Int, TokenSearchModel>() {
    private val TAG = "TokenSearchPagingSource"
    private val STARTING_PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, TokenSearchModel>): Int? {
        // If the state is empty, return null to indicate no refresh key
        if (state.anchorPosition == null) {
            return null
        }

        // Get the page index of the anchor position
        val anchorPage = state.closestPageToPosition(state.anchorPosition!!)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TokenSearchModel> {
        return try {
            val isConnected = connectivityChecker.isNetworkAvailable()

            val page = params.key ?: STARTING_PAGE_INDEX
            val pageSize = params.loadSize

            if (!isConnected) {
                return LoadResult.Error(IOException("No internet connection"))
            }

            val tokens = tokenMetricsService.getSearchTokens(query, page, pageSize)

            LoadResult.Page(
                data = tokens,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (tokens.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}