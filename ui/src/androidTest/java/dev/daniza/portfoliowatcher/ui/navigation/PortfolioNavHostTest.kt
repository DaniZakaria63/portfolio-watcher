package dev.daniza.portfoliowatcher.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SuppressLint("all")
class PortfolioNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupPortfolioNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            PortfolioNavHost(
                navController = navController,
            )
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        assertEquals(HomeDestination.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun navHost_verifyNavigationToList() {
        composeTestRule.onNodeWithContentDescription("Navigate to List")
            .performClick()

        assertEquals(MarketDestination.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun navHost_verifyNavigationToNews() {
        composeTestRule.onNodeWithContentDescription("Navigate to News")
            .performClick()

        assertEquals(NewsDestination.route, navController.currentBackStackEntry?.destination?.route)
    }
    /*
        @Test
        fun navHost_verifyNavigationToSearch() {
            composeTestRule.onNodeWithContentDescription("Navigate to Search")
                .performClick()

            assertEquals(SearchDestination.route, navController.currentBackStackEntry?.destination?.route)
        }

        @Test
        fun navHost_verifyNavigationToDetail() {
            val tokenCryptoId = "123"

            composeTestRule.runOnUiThread {
                navController.navigate("${DetailDestination.route}/$tokenCryptoId")
            }
            composeTestRule.waitForIdle()
            composeTestRule.mainClock.advanceTimeBy(300) // Give extra time for navigation to complete

            val route = navController.currentBackStackEntry?.destination?.route
            assertEquals(DetailDestination.routeWithArgs, route)

            val actualTokenId = navController.currentBackStackEntry?.arguments?.getString(DetailDestination.detailIdArgs)
            assertEquals(tokenCryptoId, actualTokenId)
        }*/
}
