package ru.netology.cryptotracker.domain

import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoinList(): Flow<List<CoinInfo>>

    suspend fun getCoinDetail(id: String): CoinInfo

    suspend fun refreshCoinList()

    fun searchCoins(name: String): Flow<List<CoinInfo>>

}