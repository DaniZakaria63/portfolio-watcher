package dev.daniza.portfoliowatcher

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.daniza.portfoliowatcher.repository.HomeSummaryRepository
import dev.daniza.portfoliowatcher.repository.HomeSummaryRepositoryImpl
import dev.daniza.portfoliowatcher.repository.NewsRepository
import dev.daniza.portfoliowatcher.repository.NewsRepositoryImpl
import dev.daniza.portfoliowatcher.repository.TokenSearchRepository
import dev.daniza.portfoliowatcher.repository.TokenSearchRepositoryImpl
import dev.daniza.portfoliowatcher.repository.UserSessionRepository
import dev.daniza.portfoliowatcher.repository.UserSessionRepositoryImpl

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

    @Binds
    abstract fun bindUserSessionRepository(
        userSessionRepositoryImpl: UserSessionRepositoryImpl
    ): UserSessionRepository

    @Binds
    abstract fun bindHomeSummaryRepository(
        homeSummaryRepositoryImpl: HomeSummaryRepositoryImpl
    ): HomeSummaryRepository
}