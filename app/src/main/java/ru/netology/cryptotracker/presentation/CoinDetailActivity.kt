package ru.netology.cryptotracker.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.cryptotracker.R
import ru.netology.cryptotracker.databinding.ActivityCoinDetailBinding
import ru.netology.cryptotracker.domain.CoinInfo

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding
    private val viewModel: CoinDetailViewModel by viewModels()
    private var coinId: String = ""

    companion object {
        private const val EXTRA_COIN_ID = "coin_id"

        fun newIntent(context: Context, coinId: String): Intent {
            return Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_COIN_ID, coinId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        coinId = intent.getStringExtra(EXTRA_COIN_ID) ?: ""
        if (coinId.isEmpty()) finish()

        setupObservers()
        viewModel.loadCoinDetails(coinId)

    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.coinDetails.collectLatest { coin ->
                coin?.let { updateUI(it) }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let { showError(it) }
            }
        }
    }

    private fun updateUI(coin: CoinInfo) {
        binding.apply {
            rank.text = coin.rank.toString()
            tvName.text = coin.name
            symbol.text = coin.symbol
            price.text = "${"%.2f".format(coin.priceUsd)}"

            val changeText = "${"%.2f".format(coin.priceChangePercentage24h)}%"
            priceChange.text = changeText
            priceChange.setTextColor(
                if (coin.priceChangePercentage24h >= 0) getColor(R.color.positive_change)
                else getColor(R.color.negative_change)
            )

            tvMarketCap.text = coin.marketCapUsd?.let { "${"%.2f".format(it)}" } ?: "N/A"
            tvVolume.text = coin.volumeUsd24h?.let { "${"%.2f".format(it)}" } ?: "N/A"
            tvCirculatingSupply.text = coin.circulatingSupply?.let {
                "${"%.2f".format(it)} ${coin.symbol}"
            } ?: "N/A"
            tvMaxSupply.text = coin.maxSupply?.let {"${"%.2f".format(it)} ${coin.symbol}"} ?: "N/A"
        }
    }

    private fun showError(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Повторить") { viewModel.loadCoinDetails(coinId) }
            .show()
    }
}