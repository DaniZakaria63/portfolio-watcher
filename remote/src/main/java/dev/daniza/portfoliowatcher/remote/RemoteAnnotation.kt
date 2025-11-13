package dev.daniza.portfoliowatcher.remote

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsRemoteOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenMetricsOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoralisOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SelfHostOkHttpClient
