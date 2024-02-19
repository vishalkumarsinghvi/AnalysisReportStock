package com.vishal.analysisreport.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader(
                    "X-RapidAPI-Key",
                    "it will not work add yours rapid api"
                )
                .build()
        )
    }
}

