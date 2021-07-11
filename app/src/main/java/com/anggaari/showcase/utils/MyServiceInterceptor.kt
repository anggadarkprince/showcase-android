package com.anggaari.showcase.utils

import com.anggaari.showcase.utils.Constants.Companion.NO_AUTH_HEADER_KEY
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class MyServiceInterceptor @Inject constructor() : Interceptor {
    private var accessToken: String? = null

    fun setAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")

        if (request.header(NO_AUTH_HEADER_KEY) == null) {
            // needs credentials
            if (accessToken == null) {
                throw RuntimeException("Access token should be defined for auth apis")
            } else {
                requestBuilder.addHeader("Authorization", "Bearer $accessToken")
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}