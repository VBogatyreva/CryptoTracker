package ru.netology.cryptotracker.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.cryptotracker.data.database.AppDatabase
import ru.netology.cryptotracker.data.database.CoinInfoDbModel
import ru.netology.cryptotracker.data.mapper.CoinInfoMapper
import ru.netology.cryptotracker.data.network.CoinApiFactory
import ru.netology.cryptotracker.domain.CoinInfo
import ru.netology.cryptotracker.domain.CoinRepository
import java.util.concurrent.TimeUnit

object CoinRepositoryImpl : CoinRepository {

    private val apiService = CoinApiFactory.coinCapCoinApiService
    private val database = AppDatabase.getInstance(CoinApiFactory.applicationContext)
    private val mapper = CoinInfoMapper()

    private val _coinList = MutableStateFlow<List<CoinInfo>>(emptyList())
    val coinList: StateFlow<List<CoinInfo>> = _coinList.asStateFlow()

    private val _coinDetails = MutableStateFlow<CoinInfo?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    private var refreshInterval = TimeUnit.SECONDS.toMillis(10)
    private var lastRefreshTime = 0L

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            database.coinPriceInfoDao().getPriceList()
                .map { dbModels -> dbModels.map { mapDbModelToDomain(it) } }
                .collect { coins ->
                    _coinList.value = coins
                }
        }
    }

    override fun getCoinList(): Flow<List<CoinInfo>> {
        return database.coinPriceInfoDao().getPriceList()
            .map { dbModels -> dbModels.map { mapDbModelToDomain(it) } }
    }

    override suspend fun getCoinDetail(id: String): CoinInfo {
        return try {
            refreshCoinDetail(id)
            database.coinPriceInfoDao().getPriceInfoAboutCoin(id)?.let { dbModel ->
                mapDbModelToDomain(dbModel)
            } ?: throw Exception("Coin details are not found")
        } catch (e: Exception) {
            throw Exception("Failed to get coin details: ${e.message}")
        }
    }

    override suspend fun refreshCoinList() {
        _isLoading.value = true
        _error.value = null

        try {
            println("Fetching coin list from API")
            val response = apiService.getCoinList()
            if (response.isSuccessful) {
                println("Received ${response.body()?.data?.size} coins")
                response.body()?.data?.let { coinsDto ->
                    val dbModels = coinsDto.map { mapper.map(it) }
                    database.coinPriceInfoDao().clearAll()
                    database.coinPriceInfoDao().insertPriceList(dbModels)
                    lastRefreshTime = System.currentTimeMillis()
                }
            } else {
                println("API error: ${response.errorBody()?.string()}")
                _error.value = "API error: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            _error.value = "Network error: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }

    private suspend fun refreshCoinDetail(id: String) {
        try {
            val response = apiService.getCoinDetail(id)
            if (response.isSuccessful) {
                response.body()?.data?.let { coinDto ->
                    val dbModel = mapper.map(coinDto)
                    database.coinPriceInfoDao().insertPriceList(listOf(dbModel))
                }
            }
        } catch (e: Exception) {
            _error.value = "Network error: ${e.message}"
        }
    }

    override fun searchCoins(name: String): Flow<List<CoinInfo>>  {
        return database.coinPriceInfoDao().getPriceList()
            .map { dbModels ->
                dbModels.filter {
                    it.name.contains(name, ignoreCase = true) ||
                            it.symbol.contains(name, ignoreCase = true)
                }.map { mapDbModelToDomain(it) }
            }
    }

    private fun mapDbModelToDomain(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            id = dbModel.id,
            rank = dbModel.rank,
            symbol = dbModel.symbol,
            name = dbModel.name,
            priceUsd = dbModel.priceUsd,
            priceChange24h = dbModel.changePercent24Hr?.let { (dbModel.priceUsd * it / 100) },
            priceChangePercentage24h = dbModel.changePercent24Hr,
            marketCapUsd = dbModel.marketCapUsd,
            volumeUsd24h = dbModel.volumeUsd24Hr,
            circulatingSupply = dbModel.supply,
            maxSupply = dbModel.maxSupply
        )
    }
}