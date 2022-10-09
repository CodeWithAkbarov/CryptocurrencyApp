package iqro.mobile.cryptocurrencyapp.presentation.coin_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobile.cryptocurrencyapp.R
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import iqro.mobile.cryptocurrencyapp.databinding.FragmentCoinListBinding
import iqro.mobile.cryptocurrencyapp.util.CoinListAdapter
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    val viewModel: CoinListViewModel by viewModels()
    private lateinit var coinListAdapter: CoinListAdapter
    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinListAdapter = CoinListAdapter()
        viewModel.getCoinList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = coinListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.observeList.collectLatest {
                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = it.message
                    }
                    UiEvent.Loading -> {
                        binding.errorTv.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is UiEvent.Success<*> -> {
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = false
                        val coinList = it.data as List<CoinModel>
                        coinListAdapter.submitList(coinList)

                    }
                }
            }
        }
    }
}