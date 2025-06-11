package ru.netology.cryptotracker.data.mapper

import org.junit.Assert.*
import org.junit.Test
import ru.netology.cryptotracker.data.network.CoinDto

class CoinInfoMapperTest {

    val mapper = CoinInfoMapper()

    @Test
    fun map() {
        val dto = CoinDto(
            id = "bitcoin",
            rank = 1,
            symbol = "BTC",
            name = "Bitcoin",
            priceUsd = 50000.0,
            changePercent24Hr = 5.0,
            marketCapUsd = 1000000000.0,
            volumeUsd24Hr = 50000000.0,
            supply = 21000000.0,
            maxSupply = 21000000.0
        )

        val result = mapper.map(dto)

        assertEquals(dto.id, result.id)
        assertEquals(dto.rank, result.rank)
        assertEquals(dto.symbol, result.symbol)
        assertEquals(dto.name, result.name)
        assertEquals(dto.priceUsd, result.priceUsd, 0.0)
        assertEquals(dto.changePercent24Hr, result.changePercent24Hr)
        assertEquals(dto.marketCapUsd, result.marketCapUsd)
        assertEquals(dto.volumeUsd24Hr, result.volumeUsd24Hr)
        assertEquals(dto.supply, result.supply)
        assertEquals(dto.maxSupply, result.maxSupply)
    }
}