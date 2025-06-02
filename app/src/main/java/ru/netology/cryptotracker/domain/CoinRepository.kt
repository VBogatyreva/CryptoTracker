package ru.netology.cryptotracker.domain

interface CoinRepository {

    suspend fun getCoinList(): List<CoinInfo>

    suspend fun getCoinDetail(id: String): CoinInfo

    suspend fun refreshCoinList()

    suspend fun searchCoins(name: String): List<CoinInfo>

}