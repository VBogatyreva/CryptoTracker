package ru.netology.cryptotracker.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.cryptotracker.databinding.ActivityCoinPriceListBinding

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding
    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var adapter: CoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        setupAutoRefresh()

        viewModel.loadCoinList()

    }
    private fun setupRecyclerView() {
        adapter = CoinAdapter(emptyList()) { coin ->
            startActivity(CoinDetailActivity.newIntent(this, coin.id))
        }
        binding.rvCoinPriceList.layoutManager = LinearLayoutManager(this)
        binding.rvCoinPriceList.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.coinList.collectLatest { coins ->
                adapter.updateList(coins)
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let { showError(it) }
            }
        }
    }

    private fun setupAutoRefresh() {
        lifecycleScope.launch {
            while (true) {
                viewModel.loadCoinList()
                delay(10000)
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        )
            .setAction("Повторить") { viewModel.loadCoinList() }
            .show()
    }
}