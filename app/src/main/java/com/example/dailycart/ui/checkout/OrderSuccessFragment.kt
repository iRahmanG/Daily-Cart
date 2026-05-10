package com.example.dailycart.ui.checkout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dailycart.R
import com.example.dailycart.databinding.FragmentOrderSuccessBinding

class OrderSuccessFragment : Fragment(R.layout.fragment_order_success) {

    private var _binding: FragmentOrderSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderSuccessBinding.bind(view)

        // Set a random/mock order ID
        binding.tvOrderID.text = "Order ID: #OX-${(10000..99999).random()}"

        binding.btnTrackOrder.setOnClickListener {
            // Navigate to tracking or orders fragment
            findNavController().navigate(R.id.ordersFragment)
        }

        binding.btnContinue.setOnClickListener {
            // Clear backstack and go home
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}