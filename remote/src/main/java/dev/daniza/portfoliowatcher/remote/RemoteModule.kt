package dev.daniza.portfoliowatcher.remote

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.daniza.portfoliowatcher.remote.endpoint.DEFAULT_REMOTE_BASE_URL
import dev.daniza.portfoliowatcher.remote.endpoint.RemoteEndpoint
import dev.daniza.portfoliowatcher.remote.service.DefaultRemoteService
import dev.daniza.portfoliowatcher.remote.service.RemoteService
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
    fun provideNetworkEndpoint(): RemoteEndpoint = Retrofit.Builder()
        .baseUrl(DEFAULT_REMOTE_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(RemoteEndpoint::class.java)

    @Singleton
    @Provides
    fun provideNetworkService(networkEndpoint: RemoteEndpoint): RemoteService =
        DefaultRemoteService(networkEndpoint)
}