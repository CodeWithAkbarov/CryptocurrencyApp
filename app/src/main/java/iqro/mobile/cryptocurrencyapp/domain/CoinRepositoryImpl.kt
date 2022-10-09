package iqro.mobile.cryptocurrencyapp.domain

import iqro.mobile.cryptocurrencyapp.common.Resource
import iqro.mobile.cryptocurrencyapp.data.CoinPaprikaApi
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import javax.inject.Inject

/**
 *Created by Zohidjon Akbarov
 */
class CoinRepositoryImpl @Inject constructor(val api: CoinPaprikaApi) : CoinRepository {
    override suspend fun getCoinList(): Resource<List<CoinModel>> {
        return try {
            val result = api.getCoinList()
            if (result.isSuccessful && result.body() != null) {
                Resource.Success(result.body())
            } else {
                Resource.Error(message = result.message())
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message)
        }
    }
}