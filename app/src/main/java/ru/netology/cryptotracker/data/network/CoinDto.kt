package ru.netology.cryptotracker.data.network

import com.google.gson.annotations.SerializedName
import ru.netology.cryptotracker.domain.CoinInfo

data class CoinDto(
    @SerializedName("id") val id: String,
    @SerializedName("rank") val rank: Int,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("priceUsd") val priceUsd: Double,
    @SerializedName("changePercent24Hr") val changePercent24Hr: Double?,
    @SerializedName("marketCapUsd") val marketCapUsd: Double?,
    @SerializedName("volumeUsd24Hr") val volumeUsd24Hr: Double?,
    @SerializedName("supply") val supply: Double?,
    @SerializedName("maxSupply") val maxSupply: Double?

) {
    fun toCoinInfo(): CoinInfo {
        return CoinInfo(
            id = id,
            rank = rank,
            symbol = symbol,
            name = name,
            priceUsd = priceUsd,
            priceChange24h = changePercent24Hr?.let { (priceUsd * it / 100) } ?: 0.0,
            priceChangePercentage24h = changePercent24Hr ?: 0.0,
            marketCapUsd = marketCapUsd,
            volumeUsd24h = volumeUsd24Hr,
            circulatingSupply = supply,
            maxSupply = maxSupply
        )
    }
}