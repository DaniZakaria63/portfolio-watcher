package dev.daniza.portfoliowatcher.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instruments_small")
data class InstrumentSmall(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val type: String,
    val currentPrice: Double? = null,
    val lastUpdated: Long? = null
)
