package dev.daniza.portfoliowatcher.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class StockEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val last_sync: Long?,
    val last_price: Double?,
    val market_cap: Double?,
    val is_favorite: Boolean = false
)