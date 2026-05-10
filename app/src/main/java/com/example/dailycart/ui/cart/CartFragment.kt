package com.example.dailycart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.R
import com.example.dailycart.databinding.FragmentCartBinding
import com.example.dailycart.ui.adapters.CartAdapter

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        val adapter = CartAdapter(emptyList()) { item, newQty ->
            viewModel.updateQuantity(item, newQty)
        }
        binding.rvCartItems.adapter = adapter

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.updateList(items)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.tvItemTotal.text = "₹$total"
            binding.tvGrandTotal.text = "₹${total + 30}" // Adding static delivery/tax
            binding.tvBottomTotal.text = "₹${total + 30}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}