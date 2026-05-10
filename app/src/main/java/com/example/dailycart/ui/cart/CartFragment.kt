package com.example.dailycart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.R
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.AppDatabase
import com.example.dailycart.databinding.FragmentCartBinding
import com.example.dailycart.ui.adapters.CartAdapter

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        val database = AppDatabase.getInstance(requireContext())
        val repository = GroceryRepository(database.cartDao())
        val factory = CartViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[CartViewModel::class.java]

        val adapter = CartAdapter(emptyList()) { item, newQty ->
            viewModel.updateQuantity(item, newQty)
        }
        binding.rvCartItems.adapter = adapter

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.updateList(items)
        }

        viewModel.itemTotal.observe(viewLifecycleOwner) { total ->
            binding.tvItemTotal.text = "₹${"%.2f".format(total)}"
        }
//        viewModel.deliveryCharge.observe(viewLifecycleOwner) { deliveryCharge ->
//            binding.tvDeliveryCharge.text = "₹${"%.2f".format(deliveryCharge)}"
//        }

        viewModel.grandTotal.observe(viewLifecycleOwner) { grandTotal ->
            binding.tvGrandTotal.text = "₹${"%.2f".format(grandTotal)}"
            binding.tvBottomTotal.text = "₹${"%.2f".format(grandTotal)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}