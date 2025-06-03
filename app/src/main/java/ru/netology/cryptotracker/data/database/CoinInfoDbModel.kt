package ru.netology.cryptotracker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_info")
data class CoinInfoDbModel(
    @PrimaryKey
    val id: String,
    val rank: Int,
    val symbol: String,
    val name: String,
    val priceUsd: Double,
    val changePercent24Hr: Double?,
    val marketCapUsd: Double?,
    val volumeUsd24Hr: Double?,
    val supply: Double?,
    val maxSupply: Double?
)