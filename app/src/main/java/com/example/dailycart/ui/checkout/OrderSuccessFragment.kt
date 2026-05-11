package com.example.dailycart.ui.checkout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.FontAssetDelegate
import com.airbnb.lottie.RenderMode
import com.example.dailycart.R
import com.example.dailycart.databinding.FragmentOrderSuccessBinding

class OrderSuccessFragment : Fragment(R.layout.fragment_order_success) {

    private var _binding: FragmentOrderSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderSuccessBinding.bind(view)

        // 1. Initial UI State: Hide text/card for a professional reveal effect
        binding.tvSuccessTitle.alpha = 0f
        binding.orderDetailCard.alpha = 0f

        // 2. Set dynamic Order ID
        binding.tvOrderID.text = "Order ID: #OX-${(10000..99999).random()}"

        // 3. Setup Lottie & Navigation
        setupProfessionalLottie()
        setupListeners()
    }

    private fun setupProfessionalLottie() {
        binding.lottieSuccess.apply {
            setRenderMode(RenderMode.SOFTWARE)
            setFontAssetDelegate(object : FontAssetDelegate() {
                override fun fetchFont(fontFamily: String?): android.graphics.Typeface? {
                    return null // Fallback to system default
                }
            })

            addAnimatorUpdateListener { animation ->
                // Reveal text and details when the checkmark is halfway done
                if (animation.animatedFraction > 0.4f) {
                    binding.tvSuccessTitle.animate().alpha(1f).setDuration(500).start()
                    binding.orderDetailCard.animate().alpha(1f).setDuration(800).start()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnTrackOrder.setOnClickListener {
            // Navigate to Order History
            findNavController().navigate(R.id.ordersFragment)
        }

        binding.btnContinue.setOnClickListener {
            // Cleanly pop the backstack to avoid returning to Checkout/Cart
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}