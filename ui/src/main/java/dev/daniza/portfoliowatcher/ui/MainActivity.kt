package dev.daniza.portfoliowatcher.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.daniza.portfoliowatcher.ui.navigation.PortfolioBottomNavigation
import dev.daniza.portfoliowatcher.ui.navigation.PortfolioNavHost
import dev.daniza.portfoliowatcher.ui.splash.SplashScreen
import dev.daniza.portfoliowatcher.ui.theme.PortfolioWatcherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioWatcherTheme {
                var showSplash by rememberSaveable { mutableStateOf(true) }

                when (showSplash) {
                    true -> {
                        SplashScreen(onNavigateToHome = {
                            showSplash = false
                        })
                    }

                    else -> {
                        // Show main app with navigation
                        val navHostController = rememberNavController()
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            bottomBar = {
                                PortfolioBottomNavigation(
                                    navHostController = navHostController
                                )
                            } ,
                            containerColor = MaterialTheme.colorScheme.background
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
    }
}