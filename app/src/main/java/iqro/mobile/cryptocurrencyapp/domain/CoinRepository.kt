package iqro.mobile.cryptocurrencyapp.domain

import iqro.mobile.cryptocurrencyapp.common.Resource
import iqro.mobile.cryptocurrencyapp.data.dto.CoinDetailModel
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel

/**
 *Created by Zohidjon Akbarov
 */
interface CoinRepository {

    suspend fun getCoinList(): Resource<List<CoinModel>>

    suspend fun getCoinDetail(coinId:String):Resource<CoinDetailModel>
}