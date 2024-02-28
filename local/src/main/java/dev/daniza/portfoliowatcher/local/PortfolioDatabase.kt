package dev.daniza.portfoliowatcher.local

import androidx.room.Database
import androidx.room.RoomDatabase

public const val PORTFOLIO_DATABASE_NAME = "theportfolio"
@Database(entities = [], version = 1, exportSchema = false)
abstract class PortfolioDatabase : RoomDatabase() {

}
