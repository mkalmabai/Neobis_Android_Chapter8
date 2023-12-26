package com.example.mobimarket.model

data class Product(
    val id: Int,
    val title: String,
    val shortDescription: String,
    val description: String,
    val price: Double,
    val productImage: String,
    val likes: List<Int>
)