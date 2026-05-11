package com.example.dailycart.ui.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.R
import com.example.dailycart.data.Repository.GroceryRepository
import com.example.dailycart.data.local.GroceryDatabase
import com.example.dailycart.databinding.FragmentOrdersBinding
import com.example.dailycart.ui.adapters.OrderAdapter

class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OrdersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrdersBinding.bind(view)

        val database = GroceryDatabase.getInstance(requireContext())
        val repository = GroceryRepository(database.cartDao())
        val factory = OrdersViewModelFactory(repository)

        // Initialize the dedicated OrdersViewModel
        viewModel = ViewModelProvider(this, factory)[OrdersViewModel::class.java]

        setupRecyclerView()
        observeOrders()
    }

    private fun setupRecyclerView() {
        binding.rvOrders.setHasFixedSize(true)
    }

    private fun observeOrders() {
        viewModel.allOrders.observe(viewLifecycleOwner) { orderList ->
            if (orderList.isNullOrEmpty()) {
                binding.rvOrders.visibility = View.GONE
                binding.emptyState.visibility = View.VISIBLE
            } else {
                binding.rvOrders.visibility = View.VISIBLE
                binding.emptyState.visibility = View.GONE
                binding.rvOrders.adapter = OrderAdapter(orderList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}