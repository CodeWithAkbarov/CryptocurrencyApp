package iqro.mobile.cryptocurrencyapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iqro.mobile.cryptocurrencyapp.R
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import iqro.mobile.cryptocurrencyapp.databinding.ItemCoinListLayoutBinding

/**
 *Created by Zohidjon Akbarov
 */

class CoinDiffUtil() : DiffUtil.ItemCallback<CoinModel>() {
    override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem
    }

}

class CoinListAdapter :
    ListAdapter<CoinModel, CoinListAdapter.CoinListViewHolder>(CoinDiffUtil()) {

    class CoinListViewHolder(private val binding: ItemCoinListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coinModel: CoinModel) {
            binding.coinNameTv.text = "${coinModel.rank}. ${coinModel.name}"
            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(
                    R.id.action_coinListFragment_to_coinDetailFragment,
                    bundleOf("coinId" to coinModel.id)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder(
            ItemCoinListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}