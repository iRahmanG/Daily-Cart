package com.example.dailycart.data.local

import androidx.room.PrimaryKey
import androidx.room3.Entity

@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val productName: String,
    val productPrice: Double,
    val quantity: Int,
    val totalPrice: Double
)
