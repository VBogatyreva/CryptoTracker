package ru.netology.cryptotracker.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CoinRepository {

    val coinList: StateFlow<List<CoinInfo>>

    fun getCoinList(): Flow<List<CoinInfo>>

    suspend fun getCoinDetail(id: String): CoinInfo

    suspend fun refreshCoinList()

    fun searchCoins(name: String): Flow<List<CoinInfo>>

}