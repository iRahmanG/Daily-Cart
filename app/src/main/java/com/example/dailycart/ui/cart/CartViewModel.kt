package com.example.dailycart.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.CartItem
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private lateinit var repository: GroceryRepository


    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                repository.delete(item)
            } else {
                repository.update(item.copy(quantity = newQuantity))
            }
        }
    }
    // LiveData for Total Price
    val totalPrice: LiveData<Double> = repository.allCartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }


}