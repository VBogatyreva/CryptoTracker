package ru.netology.cryptotracker.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cryptotracker.R
import ru.netology.cryptotracker.domain.CoinInfo

class CoinPriceListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        val recyclerView = findViewById<RecyclerView>(R.id.rvCoinPriceList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val coins = listOf(
            CoinInfo(
                id = "bitcoin",
                rank = 1,
                symbol = "BTC",
                name = "Bitcoin",
                priceUsd = 50000.0,
                priceChange24h = 1500.0,
                priceChangePercentage24h = 3.2,
                marketCapUsd = 1_000_000_000_000.0,
                volumeUsd24h = 50_000_000_000.0,
                circulatingSupply = 19_000_000.0,
                maxSupply = 21_000_000.0
            ),
            CoinInfo(
                id = "ethereum",
                rank = 2,
                symbol = "ETH",
                name = "Ethereum",
                priceUsd = 3000.0,
                priceChange24h = -75.0,
                priceChangePercentage24h = -2.5,
                marketCapUsd = 350_000_000_000.0,
                volumeUsd24h = 25_000_000_000.0,
                circulatingSupply = 120_000_000.0,
                maxSupply = null
            )
        )
        recyclerView.adapter = CoinAdapter(coins)
    }
}