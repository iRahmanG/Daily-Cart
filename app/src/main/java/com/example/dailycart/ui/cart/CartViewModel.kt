package com.example.dailycart.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: GroceryRepository) : ViewModel() {

    private val _orderSuccess = MutableLiveData<Boolean>(false)
    val orderSuccess: LiveData<Boolean> = _orderSuccess

    val cartItems: LiveData<List<CartItem>> = repository.allCartItems

    val itemTotal: LiveData<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }

    val deliveryCharge: LiveData<Double> = itemTotal.map { total ->
        if (total > 0.0) 30.0 else 0.0
    }

    val grandTotal: LiveData<Double> = itemTotal.map { total ->
        val delivery = if (total > 0.0) 30.0 else 0.0
        total + delivery
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

    fun placeOrder(paymentMethod: String) {
        viewModelScope.launch {
            val currentItems = cartItems.value ?: return@launch
            val total = grandTotal.value ?: 0.0

            val newOrder = Order(
                orderId = "OX-${(10000..99999).random()}",
                totalAmount = total,
                deliveryCharge = 30.0,
                timestamp = System.currentTimeMillis(),
                itemCount = currentItems.sumOf { it.quantity },
                paymentMethod = paymentMethod
            )

            // 1. Save to History Table
            repository.saveOrder(newOrder)

            // 2. Clear the active Cart Table
            repository.clearCart()

            // 3. Notify UI to navigate to Success Screen
            _orderSuccess.postValue(true)
        }
    }
    fun addItemToCart(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insert(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resetOrderState() {
        _orderSuccess.value = false
    }
}