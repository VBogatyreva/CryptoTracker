package ru.netology.cryptotracker.data.network

import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    @SerializedName("data") val data: CoinDto,
    @SerializedName("timestamp") val timestamp: Long
)