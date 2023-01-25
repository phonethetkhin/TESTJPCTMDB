package com.ptk.testjpctmdb.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.BODY
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 120000L
            connectTimeoutMillis = 120000L
            socketTimeoutMillis = 120000L
        }
    }

    class CustomHttpLogger() : Logger {
        override fun log(message: String) {
            Log.e("loggerTag", message) // Or whatever logging system you want here
        }
    }
}