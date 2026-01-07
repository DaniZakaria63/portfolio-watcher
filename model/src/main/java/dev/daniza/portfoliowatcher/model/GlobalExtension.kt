package dev.daniza.portfoliowatcher.model

fun String?.orDash(): String = this.orEmpty().ifEmpty { "-" }
fun Double?.orZero(): Double = (this ?: 0) as Double

// Extension functions for formatting
fun Double?.formatCurrency(): String = if(this == null) "$-" else "$%,.2f".format(this)
fun Double?.formatPercent(): String = if(this == null) "%-" else "%.2f%%".format(this)