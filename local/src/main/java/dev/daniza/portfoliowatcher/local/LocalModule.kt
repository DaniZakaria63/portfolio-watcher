package dev.daniza.portfoliowatcher.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providePortfolioDatabase(@ApplicationContext context: Context) : PortfolioDatabase =
        Room.databaseBuilder(context, PortfolioDatabase::class.java, PORTFOLIO_DATABASE_NAME).build()
}