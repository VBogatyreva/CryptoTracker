package ru.netology.cryptotracker.domain

class RefreshCoinDataUseCase (private val repository: CoinRepository) {
    suspend fun refreshCoin() {
        repository.refreshCoinList()
    }
}