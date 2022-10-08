package iqro.mobile.cryptocurrencyapp.domain

import iqro.mobile.cryptocurrencyapp.common.Resource
import iqro.mobile.cryptocurrencyapp.data.CoinPaprikaApi
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import javax.inject.Inject

/**
 *Created by Zohidjon Akbarov
 */
class CoinListRepositoryImpl @Inject constructor(val api: CoinPaprikaApi) : CoinListRepository {
    override suspend fun getCoinList(): Resource<List<CoinModel>> {
        return try {
            val response = api.getCoinList()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body())
            } else {
                Resource.Error(null, response.message())
            }
        } catch (e: Exception) {
            Resource.Error(null, e.message)
        }
    }
}