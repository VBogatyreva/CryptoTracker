package ru.netology.cryptotracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinInfoDao {
    @Query("SELECT * FROM coin_info ORDER BY rank ASC")
    fun getPriceList(): Flow<List<CoinInfoDbModel>>

    @Query("SELECT * FROM coin_info WHERE id = :id LIMIT 1")
    suspend fun getPriceInfoAboutCoin(id: String): CoinInfoDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceList(priceList: List<CoinInfoDbModel>)

    @Query("DELETE FROM coin_info")
    suspend fun clearAll()
}
