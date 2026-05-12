package com.example.dailycart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailycart.R
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.GroceryDatabase
import com.example.dailycart.databinding.FragmentCartBinding
import com.example.dailycart.ui.adapters.CartAdapter

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)


        val database = GroceryDatabase.getInstance(requireContext())
        val repository = GroceryRepository(database.cartDao())
        val factory = CartViewModelFactory(repository)

        // Use requireActivity() to ensure data consistency across the checkout flow
        viewModel = ViewModelProvider(requireActivity(), factory)[CartViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        val adapter = CartAdapter(emptyList()) { item, isIncrement->
            viewModel.updateQuantity(item, isIncrement)
        }
        binding.rvCartItems.adapter = adapter
        binding.rvCartItems.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        // Observe Cart Items
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            (binding.rvCartItems.adapter as CartAdapter).updateList(items)
        }

        // Observe Item Total
        viewModel.itemTotal.observe(viewLifecycleOwner) { total ->
            binding.tvItemTotal.text = "₹${"%.2f".format(total)}"
        }

        // Observe Delivery Charge
        viewModel.deliveryCharge.observe(viewLifecycleOwner) { delivery ->
            binding.tvDeliveryCharge.text = "₹${"%.2f".format(delivery)}"
        }

        // Observe Grand Total
        viewModel.grandTotal.observe(viewLifecycleOwner) { grandTotal ->
            binding.tvGrandTotal.text = "₹${"%.2f".format(grandTotal)}"
            binding.tvBottomTotal.text = "₹${"%.2f".format(grandTotal)}"
        }
    }

    private fun setupListeners() {
        binding.btnCheckout.setOnClickListener {
            // Only navigate if the cart isn't empty
            if (viewModel.cartItems.value?.isNotEmpty() == true) {
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}