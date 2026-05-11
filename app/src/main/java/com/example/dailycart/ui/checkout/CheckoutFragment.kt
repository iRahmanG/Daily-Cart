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
import com.example.dailycart.ui.cart.CartViewModel

class CheckoutFragment : Fragment(R.layout.fragment_checkout) {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    // Refactored to use CartViewModel
    private lateinit var cartViewModel: CartViewModel
    private var selectedPaymentMethod = "ONLINE"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCheckoutBinding.bind(view)

        // Using requireActivity() is crucial so CartFragment and CheckoutFragment
        // share the same ViewModel instance and data.
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        setupUI()
        setupPaymentSelection()
        observeCartData()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        updatePaymentSelectionUI()
    }

    private fun setupPaymentSelection() {
        binding.cardCOD.setOnClickListener {
            selectedPaymentMethod = "COD"
            updatePaymentSelectionUI()
        }

        binding.cardOnline.setOnClickListener {
            selectedPaymentMethod = "ONLINE"
            updatePaymentSelectionUI()
        }

        binding.btnPlaceOrder.setOnClickListener {
            if (cartViewModel.cartItems.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
            } else {
                binding.btnPlaceOrder.isEnabled = false
                cartViewModel.placeOrder(cart)
            }
        }
    }

    private fun updatePaymentSelectionUI() {
        // Correct resource referencing using R.color
        val activeBlue = ContextCompat.getColor(requireContext(), R.color.primary)
        val inactiveBorder = ContextCompat.getColor(requireContext(), R.color.soft_border)

        val isOnline = selectedPaymentMethod == "ONLINE"

        binding.cardOnline.strokeColor = if (isOnline) activeBlue else inactiveBorder
        binding.cardOnline.strokeWidth = if (isOnline) 6 else 2

        binding.cardCOD.strokeColor = if (!isOnline) activeBlue else inactiveBorder
        binding.cardCOD.strokeWidth = if (!isOnline) 6 else 2
    }

    private fun observeCartData() {
        // Observe totals
        cartViewModel.itemTotal.observe(viewLifecycleOwner) { subtotal ->
            val delivery = 30.0
            binding.tvFinalAmount.text = "₹${subtotal + delivery}"
        }

        // Observe checkout success
        cartViewModel.orderSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigate(R.id.action_checkout_to_success)
                cartViewModel.resetOrderState()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}