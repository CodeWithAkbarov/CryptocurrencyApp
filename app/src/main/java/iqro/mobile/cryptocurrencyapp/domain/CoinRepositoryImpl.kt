package iqro.mobile.cryptocurrencyapp.domain

import iqro.mobile.cryptocurrencyapp.common.Resource
import iqro.mobile.cryptocurrencyapp.data.CoinPaprikaApi
import iqro.mobile.cryptocurrencyapp.data.dto.CoinDetailModel
import iqro.mobile.cryptocurrencyapp.data.dto.CoinModel
import javax.inject.Inject

/**
 *Created by Zohidjon Akbarov
 */
class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaApi) : CoinRepository {
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

    override suspend fun getCoinDetail(coinId: String): Resource<CoinDetailModel> {
        return try {
            val response = api.getCoinDetail(coinId)
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