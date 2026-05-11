package com.example.dailycart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

            // Format timestamp to readable date
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            tvOrderDate.text = sdf.format(Date(order.timestamp))
        }
    }

    override fun getItemCount() = orders.size
}