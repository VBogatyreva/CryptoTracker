package ru.netology.cryptotracker.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cryptotracker.R
import ru.netology.cryptotracker.domain.CoinInfo

class CoinAdapter(
    private var coins: List<CoinInfo>,
    private val onItemClick: (CoinInfo) -> Unit
) : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    fun updateList(newList: List<CoinInfo>) {
        val diffCallback = CoinDiffCallback(coins, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        coins = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coins[position])
        holder.itemView.setOnClickListener { onItemClick(coins[position]) }
    }

    override fun getItemCount(): Int = coins.size

    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logo: ImageView = itemView.findViewById(R.id.ivLogo)
        private val rank: TextView = itemView.findViewById(R.id.rank)
        private val symbol: TextView = itemView.findViewById(R.id.symbol)
        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val priceChange: TextView = itemView.findViewById(R.id.price_change)

        fun bind(coin: CoinInfo) {
            rank.text = coin.rank.toString()
            name.text = coin.name
            symbol.text = coin.symbol
            price.text = "%.2f".format(coin.priceUsd)

            val changeText = "%.2f%%".format(coin.priceChangePercentage24h)
            priceChange.text = changeText

            val colorRes = if (coin.priceChangePercentage24h >= 0) {
                R.color.positive_change
            } else {
                R.color.negative_change
            }
            priceChange.setTextColor(ContextCompat.getColor(itemView.context, colorRes))

        }
    }

    private class CoinDiffCallback(
        private val oldList: List<CoinInfo>,
        private val newList: List<CoinInfo>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}