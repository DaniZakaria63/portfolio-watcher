package dev.daniza.portfoliowatcher.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun String?.orDash(): String = this.orEmpty().ifEmpty { "-" }
fun Double?.orZero(): Double = (this ?: 0) as Double

@OptIn(ExperimentalTime::class)
fun getCurrentTimeEpoch(): Long = Clock.System.now().toEpochMilliseconds()

// Extension functions for formatting
fun Double?.formatCurrency(): String = if(this == null) "$-" else "$%,.2f".format(this)
fun Double?.formatPercent(): String = if(this == null) "%-" else "%.2f%%".format(this)