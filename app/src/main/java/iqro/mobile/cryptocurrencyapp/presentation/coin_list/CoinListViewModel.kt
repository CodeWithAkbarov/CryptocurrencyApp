package iqro.mobile.cryptocurrencyapp.presentation.coin_list

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
class CoinListViewModel @Inject constructor(private val coinListRepository: CoinRepository) :
    ViewModel() {

    private val _observeList = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val observeList: StateFlow<UiEvent> get() = _observeList

    fun getCoinList() {
        viewModelScope.launch {
            _observeList.value = UiEvent.Loading
            when (val response = coinListRepository.getCoinList()) {
                is Resource.Error -> {
                    _observeList.value = UiEvent.Error(response.message)
                }
                is Resource.Success -> {
                    if (response.data != null) {
                        _observeList.value = UiEvent.Success(response.data)
                    } else {
                        _observeList.value = UiEvent.Error("Empty data")
                    }
                }
            }
        }
    }
}