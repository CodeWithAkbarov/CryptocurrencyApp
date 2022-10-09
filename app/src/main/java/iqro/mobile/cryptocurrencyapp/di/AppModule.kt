package iqro.mobile.cryptocurrencyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iqro.mobile.cryptocurrencyapp.data.CoinPaprikaApi
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *Created by Zohidjon Akbarov
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getCoinPaprikaApi(): CoinPaprikaApi =
        Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .build()
            .create(CoinPaprikaApi::class.java)

}