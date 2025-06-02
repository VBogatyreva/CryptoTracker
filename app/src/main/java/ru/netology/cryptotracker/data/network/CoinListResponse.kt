package ru.netology.cryptotracker.data.network

import com.google.gson.annotations.SerializedName

data class CoinListResponse(
    @SerializedName("data") val data: List<CoinDto>,
    @SerializedName("timestamp") val timestamp: Long
)
