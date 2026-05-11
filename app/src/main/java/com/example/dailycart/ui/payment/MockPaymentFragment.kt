package com.example.dailycart.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailycart.R
import com.example.dailycart.ui.cart.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MockPaymentFragment : Fragment(R.layout.fragment_mock_payment) {

    private lateinit var viewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        // Simulate payment gateway delay
        CoroutineScope(Dispatchers.Main).launch {
            delay(2500)

            // Finalize the order in the background
            viewModel.placeOrder("ONLINE")

            // Navigate to success
            findNavController().navigate(R.id.action_payment_to_success)
        }
    }
}