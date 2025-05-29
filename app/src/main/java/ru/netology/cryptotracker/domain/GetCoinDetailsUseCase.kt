package ru.netology.cryptotracker.domain

class GetCoinDetailsUseCase (private val repository: CoinRepository) {

    fun getCoinDetail(id: String): CoinInfo {
        return repository.getCoinDetail(id)
    }
}