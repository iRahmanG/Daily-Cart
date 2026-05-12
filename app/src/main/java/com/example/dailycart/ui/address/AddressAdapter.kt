package com.example.dailycart.ui.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailycart.data.model.Address
import com.example.dailycart.databinding.ItemAddressBinding

class AddressAdapter(
    private val onAddressSelected: (Address) -> Unit,
    private val onEditClick: (Address) -> Unit,
    private val onDeleteClick: (Address) -> Unit // Added missing parameter
) : ListAdapter<Address, AddressAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = getItem(position)
        holder.binding.apply {
            tvTitle.text = address.title
            tvDetails.text = "${address.streetAddress}, ${address.city}"

            root.setOnClickListener { onAddressSelected(address) }
            ivEdit.setOnClickListener { onEditClick(address) }

            // Set long click to trigger deletion
            root.setOnLongClickListener {
                onDeleteClick(address)
                true
            }
        }
    }

    class ViewHolder(val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(old: Address, new: Address) = old.id == new.id
        override fun areContentsTheSame(old: Address, new: Address) = old == new
    }
}