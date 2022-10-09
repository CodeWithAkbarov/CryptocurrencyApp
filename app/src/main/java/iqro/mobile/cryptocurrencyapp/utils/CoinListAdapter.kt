package iqro.mobile.cryptocurrencyapp.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import iqro.mobile.cryptocurrencyapp.databinding.ItemCoinListBinding

/**
 *Created by Zohidjon Akbarov
 */

class CoinDiffUtils() : DiffUtil.ItemCallback<CoinModel>() {
    override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem

    }

}
class CoinListAdapter() :
    ListAdapter<CoinModel, CoinListAdapter.CoinListViewHolder>(CoinDiffUtils()) {
    class CoinListViewHolder(private val binding: ItemCoinListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coinModel: CoinModel) {
            binding.coinNameTv.text = "${coinModel.rank}. ${coinModel.name}"
            binding.statusTv.text = if (coinModel.isActive) "Active" else "Not active"
            binding.statusTv.setTextColor(if (coinModel.isActive) Color.GREEN else Color.RED)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder(
            ItemCoinListBinding.inflate(
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