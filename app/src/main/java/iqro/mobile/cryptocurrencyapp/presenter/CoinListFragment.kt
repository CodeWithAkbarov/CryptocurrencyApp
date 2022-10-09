package iqro.mobile.cryptocurrencyapp.presenter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import iqro.mobile.cryptocurrencyapp.databinding.FragmentCoinListBinding
import iqro.mobile.cryptocurrencyapp.utils.CoinListAdapter
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoinListViewModel by viewModels()
    private val TAG = "TAG"
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCoinList()
        coinListAdapter = CoinListAdapter()
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
            layoutManager = LinearLayoutManager(requireContext())
        }

        lifecycleScope.launchWhenCreated {
            viewModel.coinListObserver.collectLatest {
                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = it.message
                        binding.recyclerView.isVisible = false
                    }
                    UiEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.errorTv.isVisible = false
                        binding.recyclerView.isVisible = false
                    }
                    is UiEvent.Success<*> -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                        binding.errorTv.isVisible = false

                        Log.d(TAG, "onViewCreated:${it.data} ")
                        val coinList = it.data as List<CoinModel>
                        coinListAdapter.submitList(coinList)
                    }
                }
            }
        }

    }

}