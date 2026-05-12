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
            Category(4, "Snacks", R.drawable.ic_snacks),
            Category(5, "Beverages", R.drawable.img_beverages),
            Category(6, "Bakery", R.drawable.img_bakery),
            Category(7, "Ice Cream", R.drawable.img_choco_cone)
        )
    }

    fun getProducts(): List<Product> {
        return listOf(
            // Vegetables
            Product(1, "Broccoli", 45.0, "500g", R.drawable.img_broccoli, "Vegetables"),
            Product(2, "Carrots", 30.0, "1kg", R.drawable.img_carrot, "Vegetables"),
            Product(3, "Spinach", 20.0, "250g", R.drawable.img_spinach, "Vegetables"),

            // Fruits
            Product(4, "Green Apple", 180.0, "1kg", R.drawable.img_apple, "Fruits"),
            Product(5, "Banana", 60.0, "1 Dozen", R.drawable.img_banana, "Fruits"),
            Product(6, "Mango", 120.0, "1kg", R.drawable.img_mango, "Fruits"),

            // Dairy
            Product(7, "Fresh Milk", 35.0, "500ml", R.drawable.img_milk, "Dairy"),
            Product(8, "Cheese", 250.0, "200g", R.drawable.img_cheese, "Dairy"),
            Product(9, "Amul Dahi", 80.0, "150g", R.drawable.img_yogurt, "Dairy"),

            // Snacks
            Product(10, "Potato Chips", 20.0, "50g", R.drawable.img_lays, "Snacks"),
            Product(11, "Chocolate Bar", 40.0, "1 unit", R.drawable.img_chocolate, "Snacks"),
            Product(12, "Salted Peanuts", 50.0, "100g", R.drawable.img_peanuts, "Snacks"),

            // Beverages
            Product(13, "Orange Juice", 110.0, "1L", R.drawable.img_juice, "Beverages"),
            Product(14, "Coffee Beans", 450.0, "250g", R.drawable.img_coffee, "Beverages"),
            Product(13, "Campa Cola Drink", 40.0, "1L", R.drawable.img_campa, "Beverages"),
            Product(14, "Coco Cola Drink", 20.0, "250g", R.drawable.img_coco_cola, "Beverages"),
            Product(13, "Sprite Drink", 40.0, "1L", R.drawable.img_sprite, "Beverages"),


            // Bakery
            Product(15, "Brown Bread", 45.0, "400g", R.drawable.img_bread, "Bakery"),
            Product(16, "Butter Croissant", 65.0, "1 unit", R.drawable.img_croissant, "Bakery"),

            Product(17, "Vanilla Bean Tub", 150.0, "500ml", R.drawable.img_vanilla_ice, "Ice Cream"),
            Product(18, "Chocolate Cone", 40.0, "1 unit", R.drawable.img_choco_cone, "Ice Cream"),
            Product(19, "Strawberry Popsicle", 25.0, "1 unit", R.drawable.img_strawberry_pop, "Ice Cream"),


        )
    }
}