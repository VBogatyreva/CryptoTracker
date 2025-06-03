package ru.netology.cryptotracker.data.mapper

import ru.netology.cryptotracker.data.database.CoinInfoDbModel
import ru.netology.cryptotracker.data.network.CoinDto

class CoinInfoMapper : Mapper<CoinDto, CoinInfoDbModel> {
    override fun map(from: CoinDto): CoinInfoDbModel {
        return CoinInfoDbModel(
            id = from.id,
            rank = from.rank,
            symbol = from.symbol,
            name = from.name,
            priceUsd = from.priceUsd,
            changePercent24Hr = from.changePercent24Hr,
            marketCapUsd = from.marketCapUsd,
            volumeUsd24Hr = from.volumeUsd24Hr,
            supply = from.supply,
            maxSupply = from.maxSupply
        )
    }
}