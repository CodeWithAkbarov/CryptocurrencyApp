package iqro.mobile.cryptocurrencyapp.data

import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import retrofit2.Response
import retrofit2.http.GET

/**
 *Created by Zohidjon Akbarov
 */
interface CoinPaprikaApi {

    @GET("v1/coins")
    suspend fun getCoinList(): Response<List<CoinModel>>


}