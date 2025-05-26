package dev.daniza.portfoliowatcher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.daniza.portfoliowatcher.ui.detail.DetailScreen
import dev.daniza.portfoliowatcher.ui.favorite.FavoriteScreen
import dev.daniza.portfoliowatcher.ui.home.HomeScreen
import dev.daniza.portfoliowatcher.ui.list.ListScreen
import dev.daniza.portfoliowatcher.ui.search.SearchScreen

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
        composable(route = FavoriteDestination.route){
            FavoriteScreen()
        }
        composable(route = SearchDestination.route){
            SearchScreen()
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
