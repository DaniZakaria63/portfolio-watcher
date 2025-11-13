package dev.daniza.portfoliowatcher

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.daniza.portfoliowatcher.network.ConnectivityObserver
import dev.daniza.portfoliowatcher.network.ConnectivityObserverImpl

@Module
@InstallIn(SingletonComponent::class)
object NetworkConnectivityModule {

    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver =
        ConnectivityObserverImpl(context)
}