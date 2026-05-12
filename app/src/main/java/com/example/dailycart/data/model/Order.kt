package com.example.dailycart.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "orders_table")
data class Order(
    @PrimaryKey val orderId: String,
    val totalAmount: Double,
    val deliveryCharge: Double,
    val timestamp: Long,
    val itemCount: Int,
    val paymentMethod: String,
    val deliveryAddress: String,
    val itemsJson: String
) : Parcelable