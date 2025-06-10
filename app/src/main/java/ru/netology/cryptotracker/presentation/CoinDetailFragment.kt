package ru.netology.cryptotracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.cryptotracker.R
import ru.netology.cryptotracker.databinding.FragmentCoinDetailBinding
import ru.netology.cryptotracker.domain.CoinInfo

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() =_binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    private val viewModel: CoinDetailViewModel by viewModels()
    private var coinId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinId = arguments?.getString("coin_id") ?: ""
        if (coinId.isEmpty()) {
            findNavController().popBackStack()
            return
        }

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

            val changePercentage = coin.priceChangePercentage24h ?: 0.0
            val changeText = "${"%.2f".format(changePercentage)}%"
            priceChange.text = changeText
            priceChange.setTextColor(
                if (changePercentage >= 0) ContextCompat.getColor(requireContext(),R.color.positive_change)
                else ContextCompat.getColor(requireContext(),R.color.negative_change)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}