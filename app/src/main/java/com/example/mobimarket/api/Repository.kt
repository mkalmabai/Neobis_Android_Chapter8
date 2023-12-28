package com.example.mobimarket.api

import com.example.mobimarket.model.LoginRequest
import com.example.mobimarket.model.RegistrationRequest

class Repository {
    suspend fun login(requestLogin: LoginRequest) = RetrofitInstance.api.login(requestLogin)
    suspend fun registration(requestRegistration: RegistrationRequest) = RetrofitInstance.api.registration(requestRegistration)
    suspend fun getAllProduct() = RetrofitInstance.api.getAllProducts()
}