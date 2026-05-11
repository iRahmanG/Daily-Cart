package com.example.dailycart.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Category
import com.example.dailycart.data.model.Product
import com.example.dailycart.utils.Constants
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: GroceryRepository) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        // Fetching from our Constants provider
        _categories.value = Constants.getCategories()
        _products.value = Constants.getProducts()
    }

    fun addToCart(product: Product) {
        val cartItem = CartItem(
            productId = product.id,
            name = product.name,
            price = product.price,
            unit = product.unit,
            imageRes = product.imageRes,
            quantity = 1,
            originalPrice = product.price
        )
        viewModelScope.launch {
            repository.insert(cartItem)
        }
    }
}