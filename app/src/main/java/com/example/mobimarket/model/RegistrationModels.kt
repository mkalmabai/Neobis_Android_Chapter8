package com.example.mobimarket.model

data class RegistrationRequest(
    val username: String,
    val email: String,
    val password1: String,
    val password2: String
)
data class RegistrationResponse(
    val access_token: String,
    val refresh_token: String,
    val user: User
)
