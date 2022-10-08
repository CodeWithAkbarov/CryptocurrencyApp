package iqro.mobile.cryptocurrencyapp.common

import retrofit2.Response

/**
 *Created by Zohidjon Akbarov
 */
sealed class Resource<T>(data: T?, message: String? = null) {
    data class Success<T>(val data: T?) : Resource<T>(data, null)
    data class Error<T>(val data: T?, val message: String?) : Resource<T>(data, message)
}
