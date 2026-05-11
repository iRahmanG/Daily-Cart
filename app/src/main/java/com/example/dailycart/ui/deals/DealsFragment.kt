package com.example.dailycart.ui.deals

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dailycart.R
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.databinding.FragmentDealsBinding

class DealsFragment : Fragment(R.layout.fragment_deals) {

    private var _binding: FragmentDealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FlashSaleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDealsBinding.bind(view)

        setupFlashSaleGrid()
    }

    private fun setupFlashSaleGrid() {
        // Initialize with dummy data
        val dealItems = listOf(
            CartItem(1, "Organic Red Apple", 144.0, "1KG", R.drawable.ic_fruit, 1,180.0),
            CartItem(2, "Robusta Bananas", 51.0, "6 Pice",R.drawable.img_banana, 1,60.0),
            CartItem(3, "Fresh Farm Tomatoes", 55.0, "1KG", R.drawable.img_tomato, 1,65.0),
            CartItem(4, "Whole Organic Milk", 34.0, "1L", R.drawable.img_milk, 1,48.0)
        )

        adapter = FlashSaleAdapter(dealItems) { item ->
            Toast.makeText(requireContext(), "Added ${item.name} to cart", Toast.LENGTH_SHORT).show()
        }

        binding.rvFlashSale.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@DealsFragment.adapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}