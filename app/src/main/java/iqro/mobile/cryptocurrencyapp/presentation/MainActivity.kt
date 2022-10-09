package iqro.mobile.cryptocurrencyapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobile.cryptocurrencyapp.R
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import iqro.mobile.cryptocurrencyapp.presentation.coin_list.CoinListViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}