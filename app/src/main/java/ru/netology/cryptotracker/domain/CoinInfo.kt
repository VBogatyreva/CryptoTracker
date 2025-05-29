package ru.netology.cryptotracker.domain

data class CoinInfo(
    // Основные идентификаторы
    val id: String,                // "bitcoin"
    val rank: Int,                 // 1 (лучше как число для сортировки)
    val symbol: String,            // "BTC"
    val name: String,              // "Bitcoin"

    // Ценовые данные
    val priceUsd: Double,          // Текущая цена в USD
    val priceChange24h: Double,     // Изменение цены за 24ч ($)
    val priceChangePercentage24h: Double, // Изменение цены за 24ч (%)

    // Рыночные метрики
    val marketCapUsd: Double?,      // Рыночная капитализация
    val volumeUsd24h: Double?,      // Объем торгов за 24ч
    val circulatingSupply: Double?, // Текущее количество в обращении
    val maxSupply: Double?,         // Максимальное возможное количество

)









