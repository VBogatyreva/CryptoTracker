package ru.netology.cryptotracker.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.netology.cryptotracker.R

class CoinDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        val id = intent.getStringExtra("COIN_ID") ?: ""
        val rank = intent.getIntExtra("COIN_RANK", 0)
        val symbol = intent.getStringExtra("COIN_SYMBOL") ?: ""
        val name = intent.getStringExtra("COIN_NAME") ?: ""
        val price = intent.getDoubleExtra("COIN_PRICE", 0.0)
        val priceChangeAmount = intent.getDoubleExtra("COIN_PRICE_CHANGE_AMOUNT", 0.0)
        val priceChangePercent = intent.getDoubleExtra("COIN_PRICE_CHANGE_PERCENT", 0.0)
        val marketCap = intent.getDoubleExtra("COIN_MARKET_CAP", 0.0)
        val volume = intent.getDoubleExtra("COIN_VOLUME", 0.0)
        val circulatingSupply = intent.getDoubleExtra("COIN_CIRCULATING_SUPPLY", 0.0)
        val maxSupply = intent.getDoubleExtra("COIN_MAX_SUPPLY", 0.0)

        findViewById<TextView>(R.id.rank).text = rank.toString()
        findViewById<TextView>(R.id.tvName).text = name
        findViewById<TextView>(R.id.symbol).text = symbol
        findViewById<TextView>(R.id.price).text = price.toString()

        val priceChangeText = String.format("%.2f%%", priceChangePercent)
        findViewById<TextView>(R.id.price_change).text = priceChangeText

        val priceChangeColor = if (priceChangePercent >= 0) {
            ContextCompat.getColor(this, R.color.positive_change)
        } else {
            ContextCompat.getColor(this, R.color.negative_change)
        }
        findViewById<TextView>(R.id.price_change).setTextColor(priceChangeColor)

        findViewById<TextView>(R.id.tvMarketCap).text = marketCap.toString()
        findViewById<TextView>(R.id.tvVolume).text = volume.toString()

        findViewById<TextView>(R.id.tvCirculatingSupply).text =
            "$circulatingSupply $symbol"

        findViewById<TextView>(R.id.tvMaxSupply).text =
            if (maxSupply > 0) "$maxSupply $symbol" else "N/A"
    }
}