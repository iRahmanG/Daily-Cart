package com.example.dailycart.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Order
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

            // 2. Save to Order History Table
            repository.saveOrder(newOrder)

            // 3. Clear the Cart Table
            repository.clearCart()

            // 4. Notify UI of success
            _orderSuccess.postValue(true)
        }
    }

    fun resetOrderState() {
        _orderSuccess.value = false
    }
}