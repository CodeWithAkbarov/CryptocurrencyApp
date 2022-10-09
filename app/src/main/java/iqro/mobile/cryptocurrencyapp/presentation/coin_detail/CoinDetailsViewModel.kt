package iqro.mobile.cryptocurrencyapp.presentation.coin_detail

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
class CoinDetailsViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {

    private val _coinDetailObserve = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val coinDetailObserve: StateFlow<UiEvent> get() = _coinDetailObserve

    fun getCoinDetail(coinId: String) {
        viewModelScope.launch {
            _coinDetailObserve.value = UiEvent.Loading
            when (val result = coinRepository.getCoinDetail(coinId)) {
                is Resource.Error -> _coinDetailObserve.value = UiEvent.Error(result.message)
                is Resource.Success -> {
                    if (result.data != null) {
                        _coinDetailObserve.value = UiEvent.Success(result.data)
                    } else {
                        _coinDetailObserve.value = UiEvent.Error("An Unknown error")
                    }
                }
            }
        }
    }
}