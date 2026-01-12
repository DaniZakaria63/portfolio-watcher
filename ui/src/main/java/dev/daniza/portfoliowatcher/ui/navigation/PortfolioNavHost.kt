/*
 * Portfolio Watcher - PortfolioNavHost.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Navigation host component managing screen routing and deep linking for the portfolio application
 */

package dev.daniza.portfoliowatcher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.daniza.portfoliowatcher.model.orDash
import dev.daniza.portfoliowatcher.ui.detail.DetailScreen
import dev.daniza.portfoliowatcher.ui.home.HomeScreen
import dev.daniza.portfoliowatcher.ui.market.MarketScreen
import dev.daniza.portfoliowatcher.ui.news.NewsScreen

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
            HomeScreen(onNavigationToMarket = {
                navController.navigateSingleTopTo(MarketDestination.routeWithArgs)
            }, onNavigateToDetail = { tokenId ->
                navController.navigateToDetail(tokenId)
            })
        }
        composable(
            route = MarketDestination.routeWithArgs,
            arguments = MarketDestination.arguments
        ){ navBackStackEntry ->
            val argType = navBackStackEntry.arguments?.getString(MarketDestination.activationArgs).orDash()
            MarketScreen(isActivation = argType == MarketDestination.activationArgs)
        }
        composable(route = NewsDestination.route) {
            NewsScreen()
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = DetailDestination.arguments,
        ){ navBackStackEntry: NavBackStackEntry ->
            val stockSymbol = navBackStackEntry.arguments?.getString(DetailDestination.detailIdArgs)
            DetailScreen(stockSymbol = stockSymbol.orDash())
        }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

private fun NavHostController.navigateToDetail(id: String) =
    this.navigateSingleTopTo("${DetailDestination.route}/$id")