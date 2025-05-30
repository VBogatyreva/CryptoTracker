package ru.netology.cryptotracker.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cryptotracker.R
import ru.netology.cryptotracker.domain.CoinInfo

class CoinAdapter(private val coins: List<CoinInfo>) : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = coins[position]
        holder.bind(coin)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CoinDetailActivity::class.java).apply {
                putExtra("COIN_ID", coin.id)
                putExtra("COIN_RANK", coin.rank)
                putExtra("COIN_SYMBOL", coin.symbol)
                putExtra("COIN_NAME", coin.name)
                putExtra("COIN_PRICE", coin.priceUsd)
                putExtra("COIN_PRICE_CHANGE_AMOUNT", coin.priceChange24h)
                putExtra("COIN_PRICE_CHANGE_PERCENT", coin.priceChangePercentage24h)
                putExtra("COIN_MARKET_CAP", coin.marketCapUsd)
                putExtra("COIN_VOLUME", coin.volumeUsd24h)
                putExtra("COIN_CIRCULATING_SUPPLY", coin.circulatingSupply)
                putExtra("COIN_MAX_SUPPLY", coin.maxSupply)
            }
            context.startActivity(intent)
        }
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
            price.text = coin.priceUsd.toString()

            val changeText = String.format("%.2f%%", coin.priceChangePercentage24h)
            priceChange.text = changeText

            val colorRes = if (coin.priceChangePercentage24h >= 0) {
                R.color.positive_change
            } else {
                R.color.negative_change
            }
            priceChange.setTextColor(ContextCompat.getColor(itemView.context, colorRes))

        }
    }
}