package com.example.dailycart.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    private lateinit var sharedCartViewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val database = GroceryDatabase.getInstance(requireContext())
        val repository = GroceryRepository(database.cartDao())

        val homeFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]

        val cartFactory = CartViewModelFactory(repository)
        sharedCartViewModel = ViewModelProvider(requireActivity(), cartFactory)[CartViewModel::class.java]

        setupObservers()
        setupListeners()
        setupSearch()
    }

    private fun setupListeners() {
        binding.llAddressBar.setOnClickListener {
            findNavController().navigate(R.id.addressListFragment)
        }
    }

    private fun setupSearch() {
        // Trigger search logic in ViewModel as user types
        binding.etSearch.addTextChangedListener { text ->
            homeViewModel.performSearch(text.toString().trim())
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

        sharedCartViewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            binding.tvSelectedAddress.text = "Delivery to: ${address.title}"
        }

        // Handle the "Not Found" state UI
        homeViewModel.isSearchResultsEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.rvProducts.visibility = View.GONE
                binding.llEmptySearch.visibility = View.VISIBLE
                binding.rvCategories.visibility = View.GONE
            } else {
                binding.rvProducts.visibility = View.VISIBLE
                binding.llEmptySearch.visibility = View.GONE
                binding.rvCategories.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}