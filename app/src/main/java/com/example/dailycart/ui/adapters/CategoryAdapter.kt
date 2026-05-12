package com.example.dailycart.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.data.model.Category
import com.example.dailycart.databinding.ItemCategoryBinding

class CategoryAdapter(private val categories: List<Category>,
                      private val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.apply {
            tvCategoryName.text = category.name
            ivCategoryIcon.setImageResource(category.iconRes)
            root.setOnClickListener { onCategoryClick(category) }
        }
    }

    override fun getItemCount() = categories.size
}