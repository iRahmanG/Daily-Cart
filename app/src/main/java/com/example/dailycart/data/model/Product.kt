package com.example.dailycart.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val unit: String,
    val imageRes: Int,
    val category: String
)