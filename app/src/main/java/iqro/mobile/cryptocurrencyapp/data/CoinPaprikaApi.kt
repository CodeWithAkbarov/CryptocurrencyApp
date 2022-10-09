package iqro.mobile.cryptocurrencyapp.data

import iqro.mobile.cryptocurrencyapp.data.dto.CoinDetailModel
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *Created by Zohidjon Akbarov
 */
interface CoinPaprikaApi {

    @GET("coins")
    suspend fun getCoinList(): Response<List<CoinModel>>

    @GET("coins/{coinId}")
    suspend fun getCoinDetail(@Path("coinId") coinId: String):Response<CoinDetailModel>


}