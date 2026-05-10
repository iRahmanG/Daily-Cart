package com.example.dailycart.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dailycart.data.model.Order

@Dao
interface GroceryDao {

    // CART OPERATIONS
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun getCartItemById(id: Int): CartItem?

    @Query("DELETE FROM cart_items")
    suspend fun deleteAllCartItems()

    // ORDER HISTORY OPERATIONS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM orders_table ORDER BY timestamp DESC")
    fun getAllOrders(): LiveData<List<Order>>
}