package dev.daniza.portfoliowatcher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import dev.daniza.portfoliowatcher.model.news.NewsHeadline
import dev.daniza.portfoliowatcher.model.tokenmetrics.TokenSearchModel
import dev.daniza.portfoliowatcher.ui.detail.DetailScreen
import dev.daniza.portfoliowatcher.ui.home.HomeScreen
import dev.daniza.portfoliowatcher.ui.list.ListScreen
import dev.daniza.portfoliowatcher.ui.news.NewsScreen
import dev.daniza.portfoliowatcher.ui.search.SearchScreen
import kotlinx.coroutines.flow.flowOf

@Composable
fun PortfolioNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier,
    ){
        composable(route = HomeDestination.route){
            HomeScreen(onNavigateToDetail = { tokenId ->
                navController.navigateToDetail(tokenId)
            })
        }
        composable(route = ListDestination.route){
            ListScreen()
        }
        composable(route = NewsDestination.route) {
            val emptyNewsFlow = flowOf(PagingData.empty<NewsHeadline>())
            val news = emptyNewsFlow.collectAsLazyPagingItems()
            NewsScreen(news = news)
        }
        composable(route = SearchDestination.route){
            val emptyTokenItems = flowOf(PagingData.empty<TokenSearchModel>())
            SearchScreen(emptyTokenItems.collectAsLazyPagingItems())
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = DetailDestination.arguments,
        ){ navBackStackEntry: NavBackStackEntry ->
            val tokenId = navBackStackEntry.arguments?.getString(DetailDestination.detailIdArgs)
            DetailScreen()
        }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

private fun NavHostController.navigateToDetail(id: String) =
    this.navigateSingleTopTo("${DetailDestination.route}/$id")