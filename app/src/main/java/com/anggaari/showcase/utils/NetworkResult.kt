package com.anggaari.showcase.utils

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null, val code: Int = 200) {

    class Success<T>(data: T, code: Int = 200): NetworkResult<T>(data, "", code)
    class Error<T>(errorMessage: String?, code: Int = 500, data: T? = null): NetworkResult<T>(data, errorMessage, code)
    class Loading<T>: NetworkResult<T>()

}