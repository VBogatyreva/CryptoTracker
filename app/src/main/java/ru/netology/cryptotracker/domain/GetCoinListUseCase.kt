package ru.netology.cryptotracker.domain

class GetCoinListUseCase (private val repository: CoinRepository) {

    suspend fun getCoinList(): List<CoinInfo> {
        return repository.getCoinList()
    }
}