package ru.netology.cryptotracker.domain

class GetCoinListUseCase (private val repository: CoinRepository) {

    fun getCoinList(): List<CoinInfo> {
        return repository.getCoinList()
    }
}