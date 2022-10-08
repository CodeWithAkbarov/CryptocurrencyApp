package iqro.mobile.cryptocurrencyapp.domain

import iqro.mobile.cryptocurrencyapp.common.Resource
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel

/**
 *Created by Zohidjon Akbarov
 */
interface CoinListRepository {

    suspend fun getCoinList(): Resource<List<CoinModel>>
}