package com.example.dailycart.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.R
import com.example.dailycart.data.model.Order
import com.example.dailycart.databinding.ItemOrderHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.apply {
            tvOrderId.text = "#${order.orderId}"
            tvTotalAmount.text = "₹${order.totalAmount}"
            tvItemCount.text = "${order.itemCount} Items"
            tvPaymentMode.text = order.paymentMethod

            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            tvOrderDate.text = sdf.format(Date(order.timestamp))

            // Handle click to show details
            root.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("order_data", order)
                }
                it.findNavController().navigate(R.id.action_orders_to_orderDetails, bundle)
            }
        }
    }

    override fun getItemCount() = orders.size
}