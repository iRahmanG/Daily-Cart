package com.example.dailycart.utils

import com.example.dailycart.data.model.Product

object Constants {
    const val FAKE_OTP = "1234"

    fun getMOckProducts(): List<Product>{
        return listOf(
            Product(1,"Apple",120.0,"Fresh Apple","Fruit","1kg"),
            Product(2,"Banana",100.0,"Fresh Banana","Fruit","1kg"),
            Product(3,"Orange",150.0,"Fresh Orange","Fruit","1kg"),
            Product(4,"Grapes",200.0,"Fresh Grapes","Fruit","1kg"),
            Product(5,"Mango",250.0,"Fresh Mango","Fruit","1kg"),
            Product(6,"Pineapple",300.0,"Fresh Pineapple","Fruit","1kg"),
            Product(7,"Watermelon",350.0,"Fresh Watermelon","Fruit","1kg"),
            Product(8,"Strawberry",400.0,"Fresh Strawberry","Fruit","1kg"),
        )
    }
}