package iqro.mobile.cryptocurrencyapp.presenter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobile.cryptocurrencyapp.common.Constants
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.databinding.CoinDetailLayoutBinding
import kotlinx.coroutines.flow.collectLatest

/**
 *Created by Zohidjon Akbarov
 */
@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val viewModel: CoinDetailViewModel by viewModels()
    private var _binding: CoinDetailLayoutBinding? = null
    val binding get() = _binding!!
    private val TAG = "TAG"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CoinDetailLayoutBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coinId = arguments?.getString(Constants.coinId)
        if (coinId != null)
            viewModel.getCoinDetail(coinId)
        lifecycleScope.launchWhenCreated {
            viewModel.coinDetailObserver.collectLatest {

                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {
                        Log.d(TAG, "onViewCreated: ${it.message}")
                    }
                    UiEvent.Loading -> {

                    }
                    is UiEvent.Success<*> -> {
                        Log.d(TAG, "onViewCreated: ${it.data}")
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}