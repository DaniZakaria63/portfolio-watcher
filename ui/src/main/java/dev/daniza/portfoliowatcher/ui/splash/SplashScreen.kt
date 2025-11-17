package dev.daniza.portfoliowatcher.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.daniza.portfoliowatcher.presenter.SplashViewModel
import dev.daniza.portfoliowatcher.ui.R
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToHome: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val isConnected by viewModel.connectionStatus.collectAsState(initial = false)
    val showWelcome by viewModel.isShowWelcome.collectAsState(initial = null)

    // Infinite pulsing animation
    var visible by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        viewModel.getCurrentSession()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        Color(0xFF3700B3)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        if (!isConnected) {
            Text(
                text = "No internet connection",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.getCurrentSession()
                }
            }) {
                Text("Retry")
            }
        } else {
            when (showWelcome) {
                null -> {
                    // Loading
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(
                            animationSpec = tween(
                                1000
                            )
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "App Icon",
                            modifier = Modifier
                                .size(120.dp)
                                .graphicsLayer(alpha = pulseAlpha),
                            tint = Color.White
                        )
                    }
                }

                true -> {
                    // Show Welcome Screen
                    WelcomeScreen(onContinue = { onNavigateToHome(1) })
                }

                false -> {
                    // Navigate to Home (assuming 1 is Home)
                    LaunchedEffect(Unit) { onNavigateToHome(1) }
                }
            }
        }

    }
}