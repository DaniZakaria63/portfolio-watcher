package dev.daniza.portfoliowatcher.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.daniza.portfoliowatcher.local.dao.InstrumentSmallDao
import dev.daniza.portfoliowatcher.local.dao.NewsDao
import dev.daniza.portfoliowatcher.local.entity.InstrumentSmall

public const val PORTFOLIO_DATABASE_NAME = "theportfolio"

@Database(entities = [InstrumentSmall::class], version = 1, exportSchema = false)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun instrumentSmallDao(): InstrumentSmallDao
    abstract fun newsDao(): NewsDao
}