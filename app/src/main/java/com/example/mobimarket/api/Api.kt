package com.example.mobimarket.api

import com.example.mobimarket.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("/api/registration/")
    suspend fun registration( @Body registration:RegistrationRequest): Response<RegistrationResponse>
    @POST("/api/v1/auth/login/")
    suspend fun login( @Body login:LoginRequest): Response<LoginResponse>
    @GET("products/all")
    suspend fun getAllProducts(): Response<List<Product>>
}