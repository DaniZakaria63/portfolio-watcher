package dev.daniza.portfoliowatcher.ui.splash

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.daniza.portfoliowatcher.presenter.NewsViewModel

@Composable
fun SplashScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    navigate: (Int) -> Unit
){

}