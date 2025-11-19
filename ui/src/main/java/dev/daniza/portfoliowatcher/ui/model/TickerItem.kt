package dev.daniza.portfoliowatcher.ui.model

data class TickerItem(
    val name: String,
    val value: String, // Current price
    val changePercent: Float, // % change from previous close
    val isPositive: Boolean, // True if current price > opening price, False otherwise
    val chartData: List<Float>, // Daily closing prices for the past week (7 days)
    val openingPrice: Float // The opening price for today, used for color logic
)