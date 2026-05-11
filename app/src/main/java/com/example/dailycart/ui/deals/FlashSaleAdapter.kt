package com.example.dailycart.ui.deals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.databinding.ItemDealProductBinding

class FlashSaleAdapter(
    private var items: List<CartItem>,
    private val onAddClick: (CartItem) -> Unit
) : RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDealProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDealProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            // Bind Data
            tvProductName.text = item.name
            tvWeight.text = "${item.unit}"
            tvPrice.text = "₹${item.price.toInt()}"

            // Calculate and show discount tag (Example: 20% OFF)
            val discount = ((item.originalPrice - item.price) / item.originalPrice * 100).toInt()
            tvDiscountTag.text = "$discount% OFF"

            ivProduct.setImageResource(item.imageRes)

            btnAdd.setOnClickListener { onAddClick(item) }
        }
    }

    override fun getItemCount() = items.size

    fun updateList(newList: List<CartItem>) {
        items = newList
        notifyDataSetChanged()
    }
}