package ru.netology.cryptotracker.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApiService {
    @GET("v3/assets")
    suspend fun getCoinList(): Response<CoinListResponse>

    @GET("v3/assets/{id}")
    suspend fun getCoinDetail(@Path("id") id: String): Response<CoinDetailResponse>
}