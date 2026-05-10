package com.example.dailycart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.data.model.Product
import com.example.dailycart.databinding.ItemProductBinding

class ProductAdapter(
    private val productList: List<Product>,
    private val onAddClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.apply {
            tvProductName.text = product.name
            tvProductUnit.text = product.unit
            tvPrice.text = "₹${product.price}"
            ivProduct.setImageResource(product.imageRes)

            btnAdd.setOnClickListener { onAddClick(product) }
        }
    }

    override fun getItemCount() = productList.size
}