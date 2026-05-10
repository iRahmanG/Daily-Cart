package com.example.dailycart.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.CartItem
import kotlinx.coroutines.launch

class CartViewModel(private val repository: GroceryRepository) : ViewModel() {
    val cartItems: LiveData<List<CartItem>> = repository.allCartItems

    val itemTotal: LiveData<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }

    val deliveryCharge: LiveData<Double> = itemTotal.map { total ->
        if (total > 0.0) 30.0 else 0.0
    }

    val grandTotal: LiveData<Double> = itemTotal.map { total ->
        if (total > 0.0) total + 30.0 else 0.0
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                repository.delete(item)
            } else {
                repository.update(item.copy(quantity = newQuantity))
            }
        }
    }
}