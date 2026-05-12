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

    private lateinit var viewModel: CartViewModel
    private var selectedPaymentMethod = "ONLINE"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCheckoutBinding.bind(view)

        // Using requireActivity() is CRITICAL to share the cart data
        // and 'orderSuccess' state with the CartFragment
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        binding.ivEdit.setOnClickListener {
            findNavController().navigate(R.id.action_checkout_to_addressList)
        }

        setupUI()
        setupPaymentSelection()
        observeCheckoutStatus()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        // Observe totals for data consistency
        viewModel.grandTotal.observe(viewLifecycleOwner) { total ->
            binding.tvFinalAmount.text = "₹${"%.2f".format(total)}"
        }
        // Observe the selected address from the ViewModel
        viewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            binding.tvAddressTitle.text = address.title
            binding.tvAddressDetails.text = "${address.streetAddress}, ${address.city}\n${address.phoneNumber}"
        }

        // Launch Address Picker
        binding.ivEdit.setOnClickListener {
            // Navigation to Address Management
            findNavController().navigate(R.id.action_checkout_to_addressList)
        }
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
            val cartItems = viewModel.cartItems.value

            if (cartItems.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.btnPlaceOrder.isEnabled = false

            if (selectedPaymentMethod == "ONLINE") {
                findNavController().navigate(R.id.action_checkout_to_mockPayment)
            } else {
                viewModel.placeOrder("COD")
            }
        }
    }

    private fun observeCheckoutStatus() {
        viewModel.orderSuccess.observe(viewLifecycleOwner) { success ->

            if (success && selectedPaymentMethod == "COD") {
                findNavController().navigate(R.id.action_checkout_to_success)
                viewModel.resetOrderState()
            }
        }
    }

    private fun updatePaymentSelectionUI() {
        val activeBlue = ContextCompat.getColor(requireContext(), R.color.primary)
        val inactiveBorder = ContextCompat.getColor(requireContext(), R.color.soft_border)

        val isOnline = selectedPaymentMethod == "ONLINE"

        // Update Card Borders
        binding.cardOnline.strokeColor = if (isOnline) activeBlue else inactiveBorder
        binding.cardOnline.strokeWidth = if (isOnline) 6 else 2

        binding.cardCOD.strokeColor = if (!isOnline) activeBlue else inactiveBorder
        binding.cardCOD.strokeWidth = if (!isOnline) 6 else 2

        // Update Radio Buttons for consistency
        binding.rbOnline.isChecked = isOnline
        binding.rbCOD.isChecked = !isOnline
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}