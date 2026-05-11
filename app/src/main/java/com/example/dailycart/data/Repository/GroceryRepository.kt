package com.example.dailycart.data.Repository

import androidx.lifecycle.LiveData
import com.example.dailycart.data.local.GroceryDao
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Order

class GroceryRepository(private val groceryDao: GroceryDao) {
    val allCartItems = groceryDao.getAllCartItems()

    val allOrders: LiveData<List<Order>> = groceryDao.getAllOrders()

    suspend fun insert(item: CartItem) {
        val existingItem = groceryDao.getCartItemById(item.productId)
        if (existingItem != null) {
            groceryDao.update(existingItem.copy(quantity = existingItem.quantity + 1))
        } else {
            groceryDao.insert(item)
        }
    }

    suspend fun delete(item: CartItem) = groceryDao.delete(item)
    suspend fun update(item: CartItem) = groceryDao.update(item)

    suspend fun clearCart(){
        groceryDao.deleteAllCartItems()
    }
    suspend fun saveOrder(order: Order) {
        groceryDao.insertOrder(order)
    }
    suspend fun completeOrder(order: Order) {
        groceryDao.insertOrder(order)
        groceryDao.deleteAllCartItems()
    }
}