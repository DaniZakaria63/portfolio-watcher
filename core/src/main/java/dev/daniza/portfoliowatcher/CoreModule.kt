package dev.daniza.portfoliowatcher

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.daniza.portfoliowatcher.repository.NewsRepository
import dev.daniza.portfoliowatcher.repository.NewsRepositoryImpl
import dev.daniza.portfoliowatcher.repository.TokenSearchRepository
import dev.daniza.portfoliowatcher.repository.TokenSearchRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class CoreModule {
    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    abstract fun bindTokenSearchRepository(
        tokenSearchRepositoryImpl: TokenSearchRepositoryImpl
    ): TokenSearchRepository
}