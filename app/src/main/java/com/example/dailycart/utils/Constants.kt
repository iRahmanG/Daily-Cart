package com.example.dailycart.utils

import com.example.dailycart.R
import com.example.dailycart.data.model.Category
import com.example.dailycart.data.model.Product

object Constants {
    fun getCategories(): List<Category> {
        return listOf(
            Category(1, "Vegetables", R.drawable.ic_veg),
            Category(2, "Fruits", R.drawable.ic_fruit),
            Category(3, "Dairy", R.drawable.ic_dairy),
            Category(4, "Snacks", R.drawable.ic_snacks)
        )
    }

    fun getProducts(): List<Product> {
        return listOf(
            Product(101, "Fresh Tomato", 40.0, "1 kg", R.drawable.img_tomato, "Vegetables"),
            Product(102, "Amul Butter", 55.0, "100 g", R.drawable.img_butter, "Dairy"),
            Product(103, "Banana", 60.0, "1 dozen", R.drawable.img_banana, "Fruits"),
            Product(104, "Lay's Classic", 20.0, "50 g", R.drawable.img_lays, "Snacks")
        )
    }
}