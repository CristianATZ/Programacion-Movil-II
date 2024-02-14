package com.example.accesologin.network.repository

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "text/xml")
            .build()

        return chain.proceed(request)
    }
}