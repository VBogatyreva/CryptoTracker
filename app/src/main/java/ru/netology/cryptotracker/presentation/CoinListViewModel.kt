package ru.netology.cryptotracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.netology.cryptotracker.data.repository.CoinRepositoryImpl
import ru.netology.cryptotracker.domain.CoinInfo

class CoinListViewModel : ViewModel() {

    private val repository = CoinRepositoryImpl

    private val _coinList = MutableStateFlow<List<CoinInfo>>(emptyList())
    val coinList: StateFlow<List<CoinInfo>> = _coinList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            repository.coinList.collect{ coins ->
                _coinList.value = coins
            }
        }
        loadCoinList()
    }

    fun loadCoinList() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.refreshCoinList()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCoins(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.searchCoins(name).collect{ coins ->
                    _coinList.value = coins
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}