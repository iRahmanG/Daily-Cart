package com.example.dailycart.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_addresses")
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val receiverName: String,
    val phoneNumber: String,
    val streetAddress: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val isDefault: Boolean = false
) : Parcelable