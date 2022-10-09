package iqro.mobile.cryptocurrencyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iqro.mobile.cryptocurrencyapp.data.CoinPaprikaApi
import iqro.mobile.cryptocurrencyapp.domain.CoinRepository
import iqro.mobile.cryptocurrencyapp.domain.CoinRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)

    @Singleton
    @Provides
    fun getCoinRepository(api: CoinPaprikaApi):CoinRepository = CoinRepositoryImpl(api)

}