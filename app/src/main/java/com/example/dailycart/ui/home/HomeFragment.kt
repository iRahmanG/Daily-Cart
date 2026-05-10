package com.example.dailycart.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.R
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.AppDatabase
import com.example.dailycart.databinding.FragmentHomeBinding
import com.example.dailycart.ui.adapters.CategoryAdapter
import com.example.dailycart.ui.adapters.ProductAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        
        val database = AppDatabase.getInstance(requireContext())
        val repository = GroceryRepository(database.cartDao())
        val factory = HomeViewModelFactory(repository)
        
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setupObservers()
    }

    private fun setupObservers() {
        // Observe Categories
        viewModel.categories.observe(viewLifecycleOwner) { categoryList ->
            binding.rvCategories.adapter = CategoryAdapter(categoryList)
        }

        // Observe Products
        viewModel.products.observe(viewLifecycleOwner) { productList ->
            binding.rvProducts.adapter = ProductAdapter(productList) { product ->
                viewModel.addToCart(product)
                Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}