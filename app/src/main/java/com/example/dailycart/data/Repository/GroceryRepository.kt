package com.example.dailycart.data.Repository

import com.example.dailycart.data.local.CartDao
import com.example.dailycart.data.local.CartItem

class GroceryRepository(private val cartDao: CartDao) {
    val allCartItems = cartDao.getAllCartItems()

    suspend fun insert(item: CartItem) = cartDao.insert(item)
    suspend fun delete(item: CartItem) = cartDao.delete(item)
    suspend fun update(item: CartItem) = cartDao.update(item)
}