package com.example.mobimarket.model

data class LoginRequest(
    val username: String,
    val email: String,
    val password: String
)
data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val user: User
)