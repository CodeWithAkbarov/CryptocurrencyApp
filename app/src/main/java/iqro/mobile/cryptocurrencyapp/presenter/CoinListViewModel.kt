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
class CoinListViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {

    private val _coinListObserver = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val conListObserver: StateFlow<UiEvent> get() = _coinListObserver

    fun getCoinList() {
        viewModelScope.launch {
            _coinListObserver.value = UiEvent.Loading
            when (val resource = coinRepository.getCoinList()) {
                is Resource.Error -> {
                    _coinListObserver.value = UiEvent.Error(resource.message)
                }
                is Resource.Success -> {
                    _coinListObserver.value = UiEvent.Success(resource.data)
                }
            }
        }
    }
}