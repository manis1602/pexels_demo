package com.manikandan.pexels

import com.manikandan.pexels.util.Constants.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class MyIntercepter: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", API_KEY)
            .build()

        return chain.proceed(request)
    }
}