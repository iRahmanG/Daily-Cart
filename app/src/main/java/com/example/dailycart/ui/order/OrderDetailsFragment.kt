package com.example.dailycart.ui.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailycart.R
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.data.model.Order
import com.example.dailycart.databinding.FragmentOrderDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderDetailsBinding.bind(view)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        val order = arguments?.getParcelable<Order>("order_data")
        order?.let { setupUI(it) }
    }

    private fun setupUI(order: Order) {
        binding.apply {
            tvOrderId.text = "Order #${order.orderId}"
            tvPaymentMethod.text = "Paid via ${order.paymentMethod}"
            tvDeliveryAddress.text = order.deliveryAddress

            val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
            tvOrderDate.text = sdf.format(Date(order.timestamp))

            tvItemTotal.text = "₹${"%.2f".format(order.totalAmount - order.deliveryCharge)}"
            tvDeliveryCharge.text = "₹${"%.2f".format(order.deliveryCharge)}"
            tvGrandTotal.text = "₹${"%.2f".format(order.totalAmount)}"

            // Deserialize JSON string back to List<CartItem>
            if (order.itemsJson.isNotEmpty()) {
                val itemType = object : TypeToken<List<CartItem>>() {}.type
                val itemsList: List<CartItem> = Gson().fromJson(order.itemsJson, itemType)

                rvOrderItems.layoutManager = LinearLayoutManager(requireContext())
                rvOrderItems.adapter = OrderItemAdapter(itemsList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}