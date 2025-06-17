package dev.daniza.portfoliowatcher.remote

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.daniza.portfoliowatcher.remote.news.DEFAULT_NEWS_REMOTE_BASE_URL
import dev.daniza.portfoliowatcher.remote.news.NewsRemote
import dev.daniza.portfoliowatcher.remote.news.NewsRemoteEndpoint
import dev.daniza.portfoliowatcher.remote.news.NewsRemoteService
import dev.daniza.portfoliowatcher.remote.service.ConnectivityChecker
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemote
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemoteEndpoint
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(DEFAULT_NEWS_REMOTE_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    @Singleton
    @Provides
    fun provideNewsRemote(retrofit: Retrofit): NewsRemote =
        NewsRemoteService(retrofit.create(NewsRemoteEndpoint::class.java))

    @Singleton
    @Provides
    fun provideTokenMetricsRemote(retrofit: Retrofit): TokenMetricsRemote {
        val newsInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("api_key", BuildConfig.API_KEY_TOKENMETRICS)
                .build()
            chain.proceed(request)
        }
        retrofit.newBuilder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(newsInterceptor)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()
            )
            .build()
        return TokenMetricsService(retrofit.create(TokenMetricsRemoteEndpoint::class.java))
    }

    @Singleton
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker =
        ConnectivityChecker(context)
}