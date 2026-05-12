package com.example.dailycart.ui.checkout

import androidx.lifecycle.*
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.model.Order
import kotlinx.coroutines.launch

class CheckoutViewModel(private val repository: GroceryRepository) : ViewModel() {

    //Observe the repository directly
    val cartItems = repository.allCartItems

    // Calculate Subtotal
    val itemTotal: LiveData<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }

    // Fixed logic for consistency: Delivery is only applied if items exist
    val deliveryCharge: LiveData<Double> = itemTotal.map { total ->
        if (total > 0.0) 30.0 else 0.0
    }

    val grandTotal: LiveData<Double> = itemTotal.map { total ->
        val delivery = if (total > 0.0) 30.0 else 0.0
        total + delivery
    }

    private val _paymentMethod = MutableLiveData("ONLINE")
    val paymentMethod: LiveData<String> = _paymentMethod

    private val _orderPlaced = MutableLiveData<Boolean>()
    val orderPlaced: LiveData<Boolean> = _orderPlaced

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    fun placeOrder() {
        viewModelScope.launch {
            val items = cartItems.value ?: return@launch
            val total = grandTotal.value ?: 0.0

            val order = Order(
                orderId = "OX-${(10000..99999).random()}",
                totalAmount = total,
                deliveryCharge = 30.0,
                timestamp = System.currentTimeMillis(),
                itemCount = items.sumOf { it.quantity },
                paymentMethod = _paymentMethod.value ?: "ONLINE"
            )
            // Atomic Transaction
            repository.saveOrder(order)
            repository.clearCart()
            _orderPlaced.postValue(true)
        }
    }


}