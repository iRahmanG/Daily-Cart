package com.example.dailycart.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.databinding.ItemOrderDetailProductBinding

class OrderItemAdapter(private val items: List<CartItem>) :
    RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOrderDetailProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderDetailProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tvProductName.text = item.name
            tvProductQuantity.text = "${item.quantity} x ₹${item.price}"
            tvProductPrice.text = "₹${"%.2f".format(item.price * item.quantity)}"
        }
    }

    override fun getItemCount() = items.size
}