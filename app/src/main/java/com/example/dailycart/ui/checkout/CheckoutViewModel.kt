package com.example.dailycart.ui.checkout

import androidx.lifecycle.*
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.model.Order
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Address
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CheckoutViewModel(private val repository: GroceryRepository) : ViewModel() {

    // 1. Observe the repository directly
    val cartItems: LiveData<List<CartItem>> = repository.allCartItems

    // 2. Add missing Address LiveData from Repository
    val allAddresses: LiveData<List<Address>> = repository.allAddresses
    private val _selectedAddress = MutableLiveData<Address>()
    val selectedAddress: LiveData<Address> = _selectedAddress

    // 3. Price Calculations
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

    private val _paymentMethod = MutableLiveData("ONLINE")
    val paymentMethod: LiveData<String> = _paymentMethod

    // 4. Fixed naming consistency to match your Fragment logic
    private val _orderSuccess = MutableLiveData<Boolean>()
    val orderSuccess: LiveData<Boolean> = _orderSuccess

    fun setAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    fun placeOrder(paymentMethod: String) {
        viewModelScope.launch {
            val currentItems = cartItems.value ?: return@launch
            val total = grandTotal.value ?: 0.0

            val address = _selectedAddress.value ?: return@launch

            val addressSnapshot = "${address.title}: ${address.streetAddress}, ${address.city}"
            val itemsSnapshotJson = Gson().toJson(currentItems)

            val newOrder = Order(
                orderId = "OX-${(10000..99999).random()}",
                totalAmount = total,
                deliveryCharge = deliveryCharge.value ?: 30.0,
                timestamp = System.currentTimeMillis(),
                itemCount = currentItems.sumOf { it.quantity },
                paymentMethod = paymentMethod,
                deliveryAddress = addressSnapshot,
                itemsJson = itemsSnapshotJson
            )

            // Atomic Database Operation
            repository.saveOrder(newOrder)
            repository.clearCart()
            _orderSuccess.postValue(true)
        }
    }

    fun resetOrderState() {
        _orderSuccess.value = false
    }
}