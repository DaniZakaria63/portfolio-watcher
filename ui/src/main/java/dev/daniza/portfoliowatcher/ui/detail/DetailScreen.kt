package dev.daniza.portfoliowatcher.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.daniza.portfoliowatcher.ui.component.LineChartCompose
import dev.daniza.portfoliowatcher.ui.theme.PortfolioWatcherTheme

@Composable
fun DetailScreen(
    stockSymbol: String,
) {

}

@Preview(showBackground = true)
@Composable
fun BoxPreview() {
    PortfolioWatcherTheme {
        Box() {

            Column {
                LineChartCompose(modifier = Modifier
                    .height(40.dp)
                    .width(90.dp))
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                )
            }
        }
    }
}