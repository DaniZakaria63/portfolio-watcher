package dev.daniza.portfoliowatcher.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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

        }
        composable(route = ListDestination.route){

        }
        composable(route = FavoriteDestination.route){

        }
        composable(route = SearchDestination.route){

        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = DetailDestination.arguments,
        ){ navBackStackEntry: NavBackStackEntry ->
            val pokemonId = navBackStackEntry.arguments?.getString(DetailDestination.detailIdArgs)

        }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

private fun NavHostController.navigateToDetail(id: String) =
    this.navigateSingleTopTo("${DetailDestination.route}/$id")
