package com.anggaari.showcase.utils

import okhttp3.ResponseBody

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null, val code: Int = 200) {

    class Success<T>(data: T, message: String = "OK", code: Int = 200): NetworkResult<T>(data, message, code)
    class Error<T>(errorMessage: String?, errorCode: Int = 500, errorBody: ResponseBody? = null): NetworkResult<T>(null, errorMessage, errorCode)
    class Loading<T>: NetworkResult<T>()

}