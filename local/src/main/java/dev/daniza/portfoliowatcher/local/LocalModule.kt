package dev.daniza.portfoliowatcher.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.daniza.portfoliowatcher.local.dao.NewsDao
import dev.daniza.portfoliowatcher.model.session.UserSession
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providePortfolioDatabase(@ApplicationContext context: Context) : PortfolioDatabase =
        Room.databaseBuilder(context, PortfolioDatabase::class.java, PORTFOLIO_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNewsDao(portfolioDatabase: PortfolioDatabase): NewsDao =
        portfolioDatabase.newsDao()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userSessionDataStore

    val Context.userSessionDataStore: DataStore<Preferences> by preferencesDataStore(
        name = UserSession.NAME
    )
}