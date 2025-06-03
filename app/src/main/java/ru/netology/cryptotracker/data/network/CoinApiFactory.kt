package ru.netology.cryptotracker.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinApiFactory {
    private const val BASE_URL = "https://rest.coincap.io/"
    private const val API_KEY = "58a5322e1d60ae2362c79eb06df13924a1963c3f6136dc2eb441a701dae3b3b7"

    lateinit var applicationContext: Context
        private set

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $API_KEY")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val coinCapCoinApiService: CoinApiService = retrofit.create(CoinApiService::class.java)
}