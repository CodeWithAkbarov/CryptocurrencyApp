package iqro.mobile.cryptocurrencyapp.presentation.coin_detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import iqro.mobile.cryptocurrencyapp.R
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.data.dto.CoinDetailModel
import iqro.mobile.cryptocurrencyapp.databinding.CoinDetailFragmentBinding
import kotlinx.coroutines.flow.collectLatest

/**
 *Created by Zohidjon Akbarov
 */

@AndroidEntryPoint
class CoinDetailFragment() : Fragment() {

    private var _binding: CoinDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CoinDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CoinDetailFragmentBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinId = arguments?.getString("coinId")
        viewModel.getCoinDetail(coinId ?: "btc-bitcoin")

        lifecycleScope.launchWhenCreated {
            viewModel.coinDetailObserve.collectLatest {
                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {
                        binding.detailLayout.isVisible = false
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = it.message
                    }
                    UiEvent.Loading -> {
                        binding.detailLayout.isVisible = false
                        binding.errorTv.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is UiEvent.Success<*> -> {
                        binding.detailLayout.isVisible = true
                        binding.errorTv.isVisible = false
                        binding.progressBar.isVisible = false
                        Log.d("TAG", "onViewCreated: ${it.data}")
                        populateData(it.data as CoinDetailModel)
                    }
                }
            }
        }
    }

    private fun populateData(detail: CoinDetailModel) {
        Glide.with(requireContext()).load(detail.logo).into(binding.coinLogImage)
        binding.coinTitleTv.text = "${detail.rank}.${detail.name} (${detail.symbol})"
        binding.statusTv.text = if (detail.isActive) "Active" else "Not active"
        binding.statusTv.setTextColor(if (detail.isActive) Color.parseColor("#00C853") else Color.RED)
        binding.descriptionTv.text = detail.description
        if (!detail.tags.isNullOrEmpty())
            detail.tags.forEach {
                binding.flowLayout.addView(
                    getTagItem(it.name)
                )
            }

    }

    private fun getTagItem(tagText: String): TextView {
        val tagTextView = TextView(requireContext())
        tagTextView.text = tagText
        tagTextView.setTextColor(Color.GREEN)
        tagTextView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        tagTextView.setPadding(30, 20, 30, 20)
        tagTextView.setBackgroundResource(R.drawable.bg_tag_item)
        return tagTextView
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}