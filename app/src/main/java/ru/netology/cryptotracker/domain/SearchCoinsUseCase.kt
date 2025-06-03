package ru.netology.cryptotracker.domain

import kotlinx.coroutines.flow.Flow

class SearchCoinsUseCase (private val repository: CoinRepository) {

    fun searchCoins(name: String): Flow<List<CoinInfo>> {
        return repository.searchCoins(name)
    }
}