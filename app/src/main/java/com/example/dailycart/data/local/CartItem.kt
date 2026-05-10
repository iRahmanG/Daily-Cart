package com.example.dailycart.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val productId: Int,
    val name: String,
    val price: Double,
    val unit: String,
    val imageRes: Int,
    var quantity: Int
)
