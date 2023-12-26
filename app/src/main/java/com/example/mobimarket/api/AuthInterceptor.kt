package com.example.mobimarket.api

import com.example.mobimarket.utils.Utils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = if(requiresAuthorization(originalRequest)){
            val token = Utils.accessToken
            val authHeader = "Bearer $token"
            originalRequest.newBuilder()
                .header("Authorization", authHeader)
                .build()
        }else{
            originalRequest
        }
        return chain.proceed(newRequest)
    }
    private fun requiresAuthorization(request: Request):Boolean{
        val path = request.url.pathSegments.joinToString("/")
        val method = request.method
        return when{
            "/products/all/" in path&&method == "GET" -> true
            else -> false
        }

    }

}