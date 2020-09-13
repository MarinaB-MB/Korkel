package com.example.client.utils

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val login: String, private val password: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder().header(
            "Authorization", Credentials.basic(login, password)
        )
        val request = builder.build()
        return chain.proceed(request)
    }
}