package com.example.dailycart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders_table")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: String,
    val totalAmount: Double,
    val deliveryCharge: Double,
    val timestamp: Long,
    val itemCount: Int,
    val paymentMethod: String
)