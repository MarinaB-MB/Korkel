package com.example.client.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder().header("Authorization", "Basic $token")
        val request = builder.build()
        return chain.proceed(request)
    }
}