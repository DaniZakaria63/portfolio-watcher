/*
 * Portfolio Watcher - PortfolioDatabase.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Room database configuration defining entities and DAO access points for local data persistence
 */

package dev.daniza.portfoliowatcher.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.daniza.portfoliowatcher.local.dao.SmallStockDao
import dev.daniza.portfoliowatcher.local.dao.NewsDao
import dev.daniza.portfoliowatcher.local.dao.StockDao
import dev.daniza.portfoliowatcher.local.entity.SmallStockEntity
import dev.daniza.portfoliowatcher.local.entity.NewsEntity
import dev.daniza.portfoliowatcher.local.entity.StockEntity

public const val PORTFOLIO_DATABASE_NAME = "theportfolio"

@Database(entities = [SmallStockEntity::class, NewsEntity::class, StockEntity::class], version = 5, exportSchema = false)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun smallStockDao(): SmallStockDao
    abstract fun newsDao(): NewsDao
    abstract fun stockDao(): StockDao
}