package ru.netology.cryptotracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.netology.cryptotracker.domain.CoinInfo
import ru.netology.cryptotracker.domain.CoinRepository
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repository: CoinRepository
)  : ViewModel() {

    private val _coinDetails = MutableStateFlow<CoinInfo?>(null)
    val coinDetails: StateFlow<CoinInfo?> = _coinDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadCoinDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _coinDetails.value = repository.getCoinDetail(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}