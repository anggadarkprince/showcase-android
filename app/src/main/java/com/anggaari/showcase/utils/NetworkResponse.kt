package com.anggaari.showcase.utils

import okhttp3.ResponseBody

sealed class NetworkResponse<T>(val body: T? = null, val errorBody: ResponseBody? = null, val message: String? = null, val code: Int = 200) {

    class Success<T>(
        data: T,
        message: String = "OK",
        code: Int = 200
    ): NetworkResponse<T>(data, null, message, code)

    class Error<T>(
        errorMessage: String?,
        errorCode: Int = 500,
        errorBody: ResponseBody? = null
    ): NetworkResponse<T>(errorBody = errorBody, message = errorMessage, code = errorCode)

    class Loading<T>: NetworkResponse<T>()

}