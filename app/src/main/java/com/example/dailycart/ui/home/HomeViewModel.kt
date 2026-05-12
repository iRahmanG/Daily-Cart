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

    private var allProductsList = listOf<Product>()
    private var currentCategory: String = "All"

    private val _isSearchResultsEmpty = MutableLiveData<Boolean>(false)
    val isSearchResultsEmpty: LiveData<Boolean> = _isSearchResultsEmpty

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        _categories.value = Constants.getCategories()
        allProductsList = Constants.getProducts()
        _products.value = allProductsList
    }
    // Filter by category
    fun filterByCategory(categoryName: String) {
        currentCategory = categoryName
        val filtered = if (categoryName == "All") {
            allProductsList
        } else {
            allProductsList.filter { it.category == categoryName } // Assumes Product has a category field
        }
        _products.value = filtered
        _isSearchResultsEmpty.value = filtered.isEmpty()
    }
    fun resetFilters() {
        currentCategory = "All"
        _products.value = allProductsList
        _isSearchResultsEmpty.value = false
    }

    fun performSearch(query: String) {
        val filteredList = if (query.isEmpty()) {
            allProductsList // Resets to full list when text is deleted
        } else {
            allProductsList.filter { it.name.contains(query, ignoreCase = true) }
        }

        _products.value = filteredList
        _isSearchResultsEmpty.value = filteredList.isEmpty()
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