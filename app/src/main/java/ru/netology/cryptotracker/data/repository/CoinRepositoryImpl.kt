package ru.netology.cryptotracker.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.netology.cryptotracker.data.network.CoinApiFactory
import ru.netology.cryptotracker.domain.CoinInfo
import ru.netology.cryptotracker.domain.CoinRepository
import java.util.concurrent.TimeUnit

object CoinRepositoryImpl : CoinRepository {

    private val apiService = CoinApiFactory.coinCapCoinApiService

    private val _coinList = MutableStateFlow<List<CoinInfo>>(emptyList())
    val coinList: StateFlow<List<CoinInfo>> = _coinList.asStateFlow()

    private val _coinDetails = MutableStateFlow<CoinInfo?>(null)

    private var refreshInterval = TimeUnit.SECONDS.toMillis(10)
    private var lastRefreshTime = 0L

    override suspend fun getCoinList(): List<CoinInfo> {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRefreshTime > refreshInterval || _coinList.value.isEmpty()) {
            refreshCoinList()
        }
        return _coinList.value
    }

    override suspend fun getCoinDetail(id: String): CoinInfo {
        refreshCoinDetail(id)
        return _coinDetails.value ?: throw Exception("Coin details aren`t available")
    }

    override suspend fun refreshCoinList() {
        try {
            println("Attempting to fetch coins...")
            val response = apiService.getCoinList()
            println("Response code: ${response.code()}")
            println("Response body: ${response.body()}")
            if (response.isSuccessful) {
                val coins = response.body()?.data?.map { it.toCoinInfo() }
                println("Fetched coins: ${coins?.size}")
                coins?.let {
                    _coinList.value = it
                    lastRefreshTime = System.currentTimeMillis()
                }
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("Exception: ${e.message}")
            throw Exception("Failed to fetch coin list: ${e.message}")
        }
    }

    private suspend fun refreshCoinDetail(id: String) {
        try {
            val response = apiService.getCoinDetail(id)
            if (response.isSuccessful) {
                _coinDetails.update { response.body()?.data?.toCoinInfo() }
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch coin details: ${e.message}")
        }
    }

    override suspend fun searchCoins(name: String): List<CoinInfo> {
        return _coinList.value.filter {
            it.name.contains(name, ignoreCase = true) ||
                    it.symbol.contains(name, ignoreCase = true)
        }
    }
}