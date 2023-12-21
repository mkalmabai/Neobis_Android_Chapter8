package com.example.mobimarket.model

data class User(
    val pk: Int,
    val username: String,
    val email: String,
    val first_name: String?,
    val last_name: String?
)