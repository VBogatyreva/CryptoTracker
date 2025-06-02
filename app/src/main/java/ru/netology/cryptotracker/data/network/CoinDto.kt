package ru.netology.cryptotracker.data.network

import com.google.gson.annotations.SerializedName
import ru.netology.cryptotracker.domain.CoinInfo

data class CoinDto(
    @SerializedName("id") val id: String,
    @SerializedName("rank") val rank: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("priceUsd") val priceUsd: String,
    @SerializedName("changePercent24Hr") val changePercent24Hr: String?,
    @SerializedName("marketCapUsd") val marketCapUsd: String?,
    @SerializedName("volumeUsd24Hr") val volumeUsd24Hr: String?,
    @SerializedName("supply") val supply: String?,
    @SerializedName("maxSupply") val maxSupply: String?
) {
    fun toCoinInfo(): CoinInfo {
        return CoinInfo(
            id = id,
            rank = rank.toInt(),
            symbol = symbol,
            name = name,
            priceUsd = priceUsd.toDouble(),
            priceChange24h = changePercent24Hr?.let { (priceUsd.toDouble() * it.toDouble() / 100) } ?: 0.0,
            priceChangePercentage24h = changePercent24Hr?.toDouble() ?: 0.0,
            marketCapUsd = marketCapUsd?.toDoubleOrNull(),
            volumeUsd24h = volumeUsd24Hr?.toDoubleOrNull(),
            circulatingSupply = supply?.toDoubleOrNull(),
            maxSupply = maxSupply?.toDoubleOrNull()
        )
    }
}