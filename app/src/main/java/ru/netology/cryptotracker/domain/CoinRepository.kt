package ru.netology.cryptotracker.domain

interface CoinRepository {

    fun getCoinList(): List<CoinInfo>

    fun getCoinDetail(id: String): CoinInfo

    fun refreshCoin()

    fun searchCoins(name: String): List<CoinInfo>

}