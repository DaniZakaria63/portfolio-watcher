package dev.daniza.portfoliowatcher.ui.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    onContinue: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            AnimatedVisibility(
                visible = pagerState.currentPage == page,
                enter = slideInHorizontally(
                    initialOffsetX = { if (page > pagerState.currentPage) 500 else -500 },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 150))
            ) {
                when (page) {
                    0 -> WelcomePage(
                        title = "Track Your Portfolio",
                        description = "Monitor your investments in real-time with detailed analytics and insights.",
                        color = MaterialTheme.colorScheme.primary
                    )
                    1 -> WelcomePage(
                        title = "Real-Time Updates",
                        description = "Get instant notifications on market changes and portfolio performance.",
                        color = MaterialTheme.colorScheme.secondary
                    )
                    2 -> WelcomePage(
                        title = "Secure and Easy",
                        description = "Manage your finances securely with an intuitive and user-friendly interface.",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }

        // Animated pagination indicator
        Spacer(modifier = Modifier.height(24.dp))
        PaginationIndicator(
            currentPage = pagerState.currentPage,
            pageCount = 3,
            selectedColor = MaterialTheme.colorScheme.primary,
            unselectedColor = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Animated Get Started button
        AnimatedVisibility(
            visible = pagerState.currentPage == 2,
            enter = scaleIn(initialScale = 0.8f) + fadeIn(),
            exit = scaleOut(targetScale = 0.8f) + fadeOut()
        ) {
            Button(
                onClick = { onContinue() },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.7f),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // Skip button animation
        AnimatedVisibility(
            visible = pagerState.currentPage < 2,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 50 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -50 })
        ) {
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(2)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Skip",
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomePage(
    title: String,
    description: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animated circle with gradient
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = 0.2f),
                            color.copy(alpha = 0.05f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (title) {
                    "Track Your Portfolio" -> Icons.Default.BarChart
                    "Real-Time Updates" -> Icons.Default.Notifications
                    else -> Icons.Default.Lock
                },
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = color
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedContent(
            targetState = title,
            transitionSpec = {
                slideInVertically(initialOffsetY = { it }) + fadeIn() with
                        slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            }
        ) { targetTitle ->
            Text(
                text = targetTitle,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        AnimatedContent(
            targetState = description,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) with
                        fadeOut(animationSpec = tween(durationMillis = 150))
            }
        ) { targetDescription ->
            Text(
                text = targetDescription,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun PaginationIndicator(
    currentPage: Int,
    pageCount: Int,
    selectedColor: Color,
    unselectedColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val animatedSize by animateDpAsState(
                targetValue = if (index == currentPage) 12.dp else 8.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "paginationIndicatorSize"
            )

            val animatedColor by animateColorAsState(
                targetValue = if (index == currentPage) selectedColor else unselectedColor,
                animationSpec = tween(durationMillis = 300),
                label = "paginationIndicatorColor"
            )

            Canvas(
                modifier = Modifier.size(animatedSize)
            ) {
                drawCircle(
                    color = animatedColor,
                    radius = animatedSize.toPx() / 2,
                    center = Offset(
                        x = size.width / 2,
                        y = size.height / 2
                    ),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}