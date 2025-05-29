package ru.netology.cryptotracker.domain

class SearchCoinsUseCase (private val repository: CoinRepository) {

    fun searchCoins(name: String): List<CoinInfo> {
        return repository.searchCoins(name)
    }
}