package com.example.dailycart.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailycart.R
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.GroceryDatabase
import com.example.dailycart.databinding.FragmentHomeBinding
import com.example.dailycart.ui.adapters.CategoryAdapter
import com.example.dailycart.ui.adapters.ProductAdapter
import com.example.dailycart.ui.cart.CartViewModel
import com.example.dailycart.ui.cart.CartViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedCartViewModel: CartViewModel // Shared for address

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val database = GroceryDatabase.getInstance(requireContext())
        val repository = GroceryRepository(database.cartDao())

        // Home Data
        val homeFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]

        // Shared Address State
        val cartFactory = CartViewModelFactory(repository)
        sharedCartViewModel = ViewModelProvider(requireActivity(), cartFactory)[CartViewModel::class.java]

        setupObservers()

        // Open Address List on click
        binding.llAddressBar.setOnClickListener {
            findNavController().navigate(R.id.addressListFragment)
        }
    }

    private fun setupObservers() {
        homeViewModel.categories.observe(viewLifecycleOwner) { list ->
            binding.rvCategories.adapter = CategoryAdapter(list)
        }

        homeViewModel.products.observe(viewLifecycleOwner) { list ->
            binding.rvProducts.adapter = ProductAdapter(list) { product ->
                homeViewModel.addToCart(product)
                Toast.makeText(requireContext(), "Added ${product.name}", Toast.LENGTH_SHORT).show()
            }
        }

        // Display real selected address
        sharedCartViewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            binding.tvSelectedAddress.text = "Delivery to: ${address.title}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}