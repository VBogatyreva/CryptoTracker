package ru.netology.cryptotracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.cryptotracker.BuildConfig
import ru.netology.cryptotracker.data.network.CoinApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
//                val original = chain.request()
//                val request = original.newBuilder()
//                    .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
//                    .method(original.method, original.body)
//                    .build()
//                chain.proceed(request)

                val request = chain.request()

                println("╔════════ Request ════════")
                println("║ URL: ${request.url}")
                println("║ Method: ${request.method}")
                request.headers.forEach { header ->
                    println("║ Header: ${header.first}=${header.second}")
                }

                val newRequest = request.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    .header("Accept", "application/json")
                    .build()

                println("╠═════ Modified Request ═════")
                newRequest.headers.forEach { header ->
                    println("║ Added Header: ${header.first}=${header.second}")
                }
                println("╚═══════════════════════════")

                val response = chain.proceed(newRequest)

                println("╔════════ Response ════════")
                println("║ Code: ${response.code}")
                println("║ Message: ${response.message}")
                response.headers.forEach { header ->
                    println("║ Header: ${header.first}=${header.second}")
                }
                println("╚═══════════════════════════")

                response
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinApiService(retrofit: Retrofit): CoinApiService {
        return retrofit.create(CoinApiService::class.java)
    }
}