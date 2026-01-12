package dev.daniza.portfoliowatcher.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import dev.daniza.portfoliowatcher.model.navigation.NavigationTarget
import dev.daniza.portfoliowatcher.ui.navigation.DetailDestination.detailIdArgs

interface BaseDestination {
    val icon: ImageVector
    val route: String
}

object SplashDestination: BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.MailOutline
    override val route: String
        get() = NavigationTarget.SPLASH.label

}

object HomeDestination: BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Home
    override val route: String
        get() = NavigationTarget.HOME.label

}

object NewsDestination : BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.MailOutline
    override val route: String
        get() = NavigationTarget.NEWS.label

}

object MarketDestination: BaseDestination {
    override val icon: ImageVector
        get() = Icons.AutoMirrored.Filled.List
    override val route: String
        get() = NavigationTarget.MARKET.label

    val activationArgs = "activation"
    val routeWithArgs = "$route/{$activationArgs}"

    val arguments = listOf(
        navArgument(detailIdArgs) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}

object SearchDestination: BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.Search
    override val route: String
        get() = NavigationTarget.SEARCH.label
}

object DetailDestination: BaseDestination {
    override val icon: ImageVector
        get() = Icons.Filled.MailOutline
    override val route: String
        get() = NavigationTarget.DETAIL.label

    val detailIdArgs = "token_id"
    val routeWithArgs ="$route/{$detailIdArgs}"

    val arguments = listOf(
        navArgument(detailIdArgs) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
    val deepLink = listOf(
        navDeepLink { uriPattern = "token://$route/{$detailIdArgs}" }
    )
}