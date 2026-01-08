package dev.daniza.portfoliowatcher.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "small_stock")
data class SmallStockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val symbol: String,
    val type: String,
    val currentPrice: Double? = null,
    val lastUpdated: Long? = null
)
