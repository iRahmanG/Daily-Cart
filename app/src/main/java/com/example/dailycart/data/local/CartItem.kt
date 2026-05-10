package com.example.dailycart.data.local

import androidx.room.PrimaryKey
import androidx.room3.Entity

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    var quantity: Int
)
