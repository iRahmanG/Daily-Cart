package com.example.dailycart.ui.cart

import androidx.lifecycle.*
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Address
import com.example.dailycart.data.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: GroceryRepository) : ViewModel() {

    private val _orderSuccess = MutableLiveData<Boolean>(false)
    val orderSuccess: LiveData<Boolean> = _orderSuccess

    val cartItems: LiveData<List<CartItem>> = repository.allCartItems
    val allAddresses: LiveData<List<Address>> = repository.allAddresses

    private val _selectedAddress = MutableLiveData<Address>()
    val selectedAddress: LiveData<Address> = _selectedAddress

    val itemTotal: LiveData<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }

    val deliveryCharge: LiveData<Double> = itemTotal.map { total ->
        if (total > 0.0) 30.0 else 0.0
    }

    val grandTotal: LiveData<Double> = MediatorLiveData<Double>().apply {
        addSource(itemTotal) { total ->
            value = total + (deliveryCharge.value ?: 0.0)
        }
        addSource(deliveryCharge) { delivery ->
            value = (itemTotal.value ?: 0.0) + delivery
        }
    }

    // Address Actions

    fun setAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAddress(address)
        }
    }

    fun saveAddress(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveAddress(address)
        }
    }

    //Cart Actions

    fun addItemToCart(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insert(item.copy(quantity = 1))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (newQuantity > 0) {
                repository.update(cartItem.copy(quantity = newQuantity))
            } else {
                repository.delete(cartItem)
            }
        }
    }

    fun placeOrder(paymentMethod: String) {
        viewModelScope.launch {
            val currentItems = cartItems.value ?: return@launch
            val total = grandTotal.value ?: 0.0
            val delivery = deliveryCharge.value ?: 0.0

            val newOrder = Order(
                orderId = "OX-${(10000..99999).random()}",
                totalAmount = total,
                deliveryCharge = delivery,
                timestamp = System.currentTimeMillis(),
                itemCount = currentItems.sumOf { it.quantity },
                paymentMethod = paymentMethod
            )

            repository.saveOrder(newOrder)
            repository.clearCart()
            _orderSuccess.postValue(true)
        }
    }

    fun resetOrderState() { _orderSuccess.value = false }
}