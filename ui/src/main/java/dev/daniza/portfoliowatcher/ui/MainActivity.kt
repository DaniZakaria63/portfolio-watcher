package dev.daniza.portfoliowatcher.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.daniza.portfoliowatcher.ui.navigation.PortfolioBottomNavigation
import dev.daniza.portfoliowatcher.ui.navigation.PortfolioNavHost
import dev.daniza.portfoliowatcher.ui.theme.PortfolioWatcherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioWatcherTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        PortfolioBottomNavigation(
                            navHostController = navHostController
                        )
                    }
                ) {
                    PortfolioNavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navHostController
                    )
                }
            }
        }
    }
}