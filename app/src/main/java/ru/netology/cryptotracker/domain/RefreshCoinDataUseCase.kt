package ru.netology.cryptotracker.domain

class RefreshCoinDataUseCase (private val repository: CoinRepository) {
    fun refreshCoin() {
        repository.refreshCoin()
    }
}