package com.example.dailycart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.data.local.CartItem
import com.example.dailycart.databinding.ItemCartBinding

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onQuantityChanged: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.binding.apply {
            tvProductName.text = item.name
            tvProductUnit.text = "1 Unit"
            tvPrice.text = "₹${item.price}"
            tvQuantity.text = item.quantity.toString()
            ivProduct.setImageResource(item.imageRes)

            btnPlus.setOnClickListener {
                onQuantityChanged(item, item.quantity + 1)
            }

            btnMinus.setOnClickListener {
                if (item.quantity > 1) {
                    onQuantityChanged(item, item.quantity - 1)
                } else {
                    // If quantity is 1 and minus is pressed, remove from cart
                    onQuantityChanged(item, 0)
                }
            }
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateList(newList: List<CartItem>) {
        cartItems = newList
        notifyDataSetChanged()
    }
}