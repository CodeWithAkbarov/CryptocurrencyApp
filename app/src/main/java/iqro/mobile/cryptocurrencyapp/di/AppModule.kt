package iqro.mobile.cryptocurrencyapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iqro.mobile.cryptocurrencyapp.data.CoinPaprikaApi
import iqro.mobile.cryptocurrencyapp.domain.CoinRepository
import iqro.mobile.cryptocurrencyapp.domain.CoinRepositoryImpl
import okhttp3.OkHttpClient
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
    fun getCoinPaprikaApi(@ApplicationContext context: Context): CoinPaprikaApi =
        Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .client(OkHttpClient.Builder().addInterceptor(ChuckerInterceptor(context)).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)

    @Singleton
    @Provides
    fun getCoinRepository(api: CoinPaprikaApi): CoinRepository = CoinRepositoryImpl(api)

}