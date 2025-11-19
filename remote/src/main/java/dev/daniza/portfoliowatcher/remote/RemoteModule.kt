/*
 * Portfolio Watcher - RemoteModule.kt
 *
 * Main Author: Dani Zakaria
 * Email: dani.zakaria@proton.me
 * GitHub: @danizakaria63
 * Created: November 2025
 * Last Modified: November 19, 2025
 *
 * Description: Dagger Hilt module providing Retrofit instances and HTTP clients for remote API communication
 */

package dev.daniza.portfoliowatcher.remote

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.daniza.portfoliowatcher.remote.BuildConfig.TAG
import dev.daniza.portfoliowatcher.remote.moralis.MoralisRemote
import dev.daniza.portfoliowatcher.remote.moralis.MoralisRemoteEndpoint
import dev.daniza.portfoliowatcher.remote.moralis.MoralisRemoteService
import dev.daniza.portfoliowatcher.remote.news.DEFAULT_NEWS_REMOTE_BASE_URL
import dev.daniza.portfoliowatcher.remote.news.NewsRemote
import dev.daniza.portfoliowatcher.remote.news.NewsRemoteEndpoint
import dev.daniza.portfoliowatcher.remote.news.NewsRemoteService
import dev.daniza.portfoliowatcher.remote.selfhost.SELFHOST_BASE_URL
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostRemote
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostRemoteEndpoint
import dev.daniza.portfoliowatcher.remote.selfhost.SelfHostService
import dev.daniza.portfoliowatcher.remote.service.ConnectivityChecker
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemote
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsRemoteEndpoint
import dev.daniza.portfoliowatcher.remote.tokenmetrics.TokenMetricsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(DEFAULT_NEWS_REMOTE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setStrictness(Strictness.LENIENT).create()))
        .build()

    @NewsRemoteOkHttpClient
    @Provides
    fun provideNewsRemoteOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsRemote(
        retrofit: Retrofit,
        @NewsRemoteOkHttpClient okHttpClient: OkHttpClient,
    ): NewsRemote {
        val newsRetrofit = retrofit.newBuilder().client(okHttpClient).build()
        return NewsRemoteService(newsRetrofit.create(NewsRemoteEndpoint::class.java))
    }

    @TokenMetricsOkHttpClient
    @Provides
    fun provideTokenMetricsOkHttpClient(): OkHttpClient {
        val newsInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", BuildConfig.API_KEY_TOKENMETRICS)
                .build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(newsInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenMetricsRemote(
        retrofit: Retrofit,
        @TokenMetricsOkHttpClient okHttpClient: OkHttpClient
    ): TokenMetricsRemote {
        retrofit.newBuilder()
            .client(okHttpClient)
            .build()
        return TokenMetricsService(retrofit.create(TokenMetricsRemoteEndpoint::class.java))
    }

    @MoralisOkHttpClient
    @Provides
    fun provideMoralisOkHttpClient(): OkHttpClient{
        val moralisInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-Key", BuildConfig.API_KEY_MORALIS)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(moralisInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoralisRemote(
        retrofit: Retrofit,
        @MoralisOkHttpClient okHttpClient: OkHttpClient
    ): MoralisRemote {
        retrofit.newBuilder()
            .client(okHttpClient)
            .build()
        return MoralisRemoteService(retrofit.create(MoralisRemoteEndpoint::class.java))
    }

    @Provides
    @SelfHostOkHttpClient
    fun provideSelfHostOkHttpClient(): OkHttpClient {
        val selfHostInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()

            // Log the request details
            Log.d(TAG, "SelfHost Request: ${request.method} ${request.url}")
            Log.d(TAG, "SelfHost Request Headers: ${request.headers}")
            request.body?.let { body ->
                val bodyString = body.toString()
                Log.d(TAG, "SelfHost Request Body: $bodyString")
            }

            val response = chain.proceed(request)

            // Log the response details
            Log.d(TAG, "SelfHost Response Code: ${response.code}")
            Log.d(TAG, "SelfHost Response Headers: ${response.headers}")
            response.body.let { responseBody ->
                val responseString = responseBody.string()
                Log.d(TAG, "SelfHost Response Body: $responseString")
                return@Interceptor response.newBuilder()
                    .body(responseString.toResponseBody(responseBody.contentType()))
                    .build()
            }

            response
        }
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(selfHostInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }


    @Singleton
    @Provides
    fun provideSelfHostRemote(
        retrofit: Retrofit,
        @SelfHostOkHttpClient okHttpClient: OkHttpClient
    ): SelfHostRemote {
        val selfHostRetrofit = retrofit.newBuilder()
            .baseUrl(SELFHOST_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setStrictness(Strictness.LENIENT).create()))
            .build()
        return SelfHostService(selfHostRetrofit.create(SelfHostRemoteEndpoint::class.java))
    }

    @Singleton
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker =
        ConnectivityChecker(context)

}