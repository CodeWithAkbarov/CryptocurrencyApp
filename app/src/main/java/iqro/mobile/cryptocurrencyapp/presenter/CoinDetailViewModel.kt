package iqro.mobile.cryptocurrencyapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iqro.mobile.cryptocurrencyapp.common.Resource
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.domain.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Zohidjon Akbarov
 */

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {


    private val _coinDetailObserver = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val coinDetailObserver: StateFlow<UiEvent> get() = _coinDetailObserver

    fun getCoinDetail(coinId: String) {
        viewModelScope.launch {
            _coinDetailObserver.value = UiEvent.Loading
            when (val response = coinRepository.getCoinDetail(coinId)) {
                is Resource.Error -> {
                    _coinDetailObserver.value = UiEvent.Error(response.message)
                }
                is Resource.Success -> {
                    _coinDetailObserver.value = UiEvent.Success(response.data)
                }
            }

        }
    }

}