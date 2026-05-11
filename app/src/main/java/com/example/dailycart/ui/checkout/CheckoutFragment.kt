package com.example.dailycart.ui.checkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailycart.R
import com.example.dailycart.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment(R.layout.fragment_checkout) {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var checkoutViewModel: CheckoutViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCheckoutBinding.bind(view)

        // Initialize the dedicated ViewModel
        checkoutViewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]

        setupListeners()
        observeData()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        binding.cardCOD.setOnClickListener { checkoutViewModel.setPaymentMethod("COD") }
        binding.cardOnline.setOnClickListener { checkoutViewModel.setPaymentMethod("ONLINE") }

        binding.btnPlaceOrder.setOnClickListener {
            binding.btnPlaceOrder.isEnabled = false // Prevent double clicks
            checkoutViewModel.placeOrder()
        }
    }

    private fun observeData() {
        // Observe Price
        checkoutViewModel.grandTotal.observe(viewLifecycleOwner) { total ->
            binding.tvFinalAmount.text = "₹$total"
        }

        // Observe Payment Method changes to update UI borders
        checkoutViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            updatePaymentUI(method)
        }

        // Observe Order Success
        checkoutViewModel.orderPlaced.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigate(R.id.action_checkout_to_success)
            }
        }
    }

    private fun updatePaymentUI(method: String) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.primary)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.soft_border)

        val isOnline = method == "ONLINE"

        binding.cardOnline.strokeColor = if (isOnline) activeColor else inactiveColor
        binding.cardOnline.strokeWidth = if (isOnline) 6 else 2

        binding.cardCOD.strokeColor = if (!isOnline) activeColor else inactiveColor
        binding.cardCOD.strokeWidth = if (!isOnline) 6 else 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}