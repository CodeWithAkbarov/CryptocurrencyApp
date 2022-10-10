package iqro.mobile.cryptocurrencyapp.presenter

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
import iqro.mobile.cryptocurrencyapp.common.Constants
import iqro.mobile.cryptocurrencyapp.common.UiEvent
import iqro.mobile.cryptocurrencyapp.data.dto.CoinDetailModel
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

                        binding.detailLayout.isVisible = false
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = it.message
                    }
                    UiEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.detailLayout.isVisible = false
                        binding.errorTv.isVisible = false
                    }
                    is UiEvent.Success<*> -> {
                        binding.detailLayout.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = false
                        Log.d(TAG, "onViewCreated: ${it.data}")
                        populateData(it.data as CoinDetailModel)
                    }
                }
            }

        }
    }

    private fun populateData(coinDetail: CoinDetailModel) {
        Glide.with(requireContext()).load(coinDetail.logo).into(binding.coinImageView)
        binding.coinNameTv.text = "${coinDetail.rank}. ${coinDetail.name}(${coinDetail.symbol})"
        binding.statusTv.text = if (coinDetail.isActive) "Active" else "Not active"
        binding.statusTv.setTextColor(if (coinDetail.isActive) Color.GREEN else Color.RED)
        binding.descriptionTv.text = coinDetail.description

        if (!coinDetail.tags.isNullOrEmpty()) {
            coinDetail.tags.forEach {
                binding.flowLayout.addView(getTagTextView(it.name))
            }
        }

    }

    private fun getTagTextView(tagString: String): TextView {
        val textView = TextView(requireContext())
        textView.text = tagString
        textView.setTextColor(Color.GREEN)
        textView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.setPadding(30, 20, 30, 20)
        textView.setBackgroundResource(R.drawable.bg_tag)
        return textView


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}