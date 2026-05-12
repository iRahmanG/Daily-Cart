package com.example.dailycart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val unit: String,
    val imageRes: Int,
    val category: String
) : Parcelable