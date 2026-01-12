package dev.daniza.portfoliowatcher.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.daniza.portfoliowatcher.model.parser.isTrue
import dev.daniza.portfoliowatcher.model.state.StateUI
import dev.daniza.portfoliowatcher.presenter.MainViewModel
import dev.daniza.portfoliowatcher.ui.navigation.PortfolioBottomNavigation
import dev.daniza.portfoliowatcher.ui.navigation.PortfolioNavHost
import dev.daniza.portfoliowatcher.ui.splash.SplashErrorDialog
import dev.daniza.portfoliowatcher.ui.splash.SplashScreen
import dev.daniza.portfoliowatcher.ui.splash.WelcomeScreen
import dev.daniza.portfoliowatcher.ui.theme.PortfolioWatcherTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreenState = installSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            mainViewModel.tokenState.collect { state ->
                splashScreenState.setKeepOnScreenCondition {
                    when(state){
                        is StateUI.Loading -> true
                        else -> false
                    }
                }
            }
        }

        setContent {
            PortfolioWatcherTheme {

                SplashProcess(mainViewModel) {
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

@Composable
fun SplashProcess(
    mainViewModel: MainViewModel,
    onContinue: @Composable ()-> Unit,
){

    val coroutineScope = rememberCoroutineScope()
    val tokenState by mainViewModel.tokenState.collectAsStateWithLifecycle(
        initialValue = StateUI.Loading
    )

    when(tokenState){
        is StateUI.Error -> {
            SplashErrorDialog(
                errorMessage = (tokenState as StateUI.Error).throwable.message.orEmpty()
                    .ifEmpty { "An unexpected error occurred." },
                onDismiss = { },
                onRetry = {
                    coroutineScope.launch { mainViewModel.getCurrentSession() }
                }
            )
        }

        is StateUI.Data -> {
            if((tokenState as StateUI.Data<Boolean>).value.isTrue()){
                WelcomeScreen(onContinue = {
                    coroutineScope.launch {
                        mainViewModel.updateTokenValue(state = false)
                    }
                })
            }else{
                onContinue()
            }
        }
        else ->  {}
    }

}