package ru.netology.cryptotracker.domain

import kotlinx.coroutines.flow.Flow

class GetCoinListUseCase (private val repository: CoinRepository) {

    fun getCoinList(): Flow<List<CoinInfo>> {
        return repository.getCoinList()
    }
}