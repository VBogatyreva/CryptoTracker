package ru.netology.cryptotracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.cryptotracker.data.database.CoinInfoDao
import ru.netology.cryptotracker.data.mapper.CoinInfoMapper
import ru.netology.cryptotracker.data.network.CoinApiService
import ru.netology.cryptotracker.data.repository.CoinRepositoryImpl
import ru.netology.cryptotracker.domain.CoinRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCoinRepository(
        coinInfoDao: CoinInfoDao,
        coinApiService: CoinApiService,
        coinMapper: CoinInfoMapper
    ): CoinRepository {
        return CoinRepositoryImpl(
            coinInfoDao,
            coinApiService,
            coinMapper
        )
    }

    @Provides
    @Singleton
    fun provideCoinInfoMapper(): CoinInfoMapper = CoinInfoMapper()
}