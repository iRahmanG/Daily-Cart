package com.example.dailycart.ui.address

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailycart.R
import com.example.dailycart.data.model.Address
import com.example.dailycart.databinding.FragmentAddEditAddressBinding
import com.example.dailycart.ui.cart.CartViewModel

class AddEditAddressFragment : Fragment(R.layout.fragment_add_edit_address) {

    private var _binding: FragmentAddEditAddressBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel

    private var editingAddress: Address? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEditAddressBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        editingAddress = arguments?.getParcelable("address_data")

        editingAddress?.let { address ->
            binding.toolbar.title = "Edit Address"
            setupEditMode(address)
        }

        binding.btnSaveAddress.setOnClickListener {
            saveAddress()
        }
    }

    private fun setupEditMode(address: Address) {
        binding.apply {
            etTitle.setText(address.title)
            etName.setText(address.receiverName)
            etPhone.setText(address.phoneNumber)
            etStreet.setText(address.streetAddress)
            etCity.setText(address.city)
            cbDefault.isChecked = address.isDefault
            btnSaveAddress.text = "Update Address"
        }
    }

    private fun saveAddress() {
        val title = binding.etTitle.text.toString()
        val name = binding.etName.text.toString()
        val phone = binding.etPhone.text.toString()
        val street = binding.etStreet.text.toString()
        val city = binding.etCity.text.toString()

        if (validateInput(name, phone, street)) {
            val address = Address(
                // Use the ID from the address we are editing, or 0 if new
                id = editingAddress?.id ?: 0,
                title = title.ifBlank { "Other" },
                receiverName = name,
                phoneNumber = phone,
                streetAddress = street,
                city = city,
                state = "CA",
                zipCode = "94025",
                isDefault = binding.cbDefault.isChecked
            )

            viewModel.saveAddress(address)
            findNavController().popBackStack()
        }
    }

    private fun validateInput(name: String, phone: String, street: String): Boolean {
        var isValid = true
        if (name.isBlank()) {
            binding.etName.error = "Name is required"
            isValid = false
        }
        if (phone.length < 10) {
            binding.etPhone.error = "Enter a valid phone number"
            isValid = false
        }
        if (street.isBlank()) {
            binding.etStreet.error = "Address cannot be empty"
            isValid = false
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}