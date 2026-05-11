package com.example.dailycart.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.model.Order

class OrdersViewModel(private val repository: GroceryRepository) : ViewModel() {


    val allOrders: LiveData<List<Order>> = repository.allOrders
}