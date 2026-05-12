package com.example.dailycart.ui.address

import android.os.Bundle
import android.view.View
import android.widget.Toast // Added missing import
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailycart.R
import com.example.dailycart.databinding.FragmentAddressListBinding
import com.example.dailycart.ui.cart.CartViewModel

class AddressListFragment : Fragment(R.layout.fragment_address_list) {

    private var _binding: FragmentAddressListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddressListBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        setupRecyclerView()

        binding.fabAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressList_to_addEditAddress)
        }
    }

    private fun setupRecyclerView() {
        val adapter = AddressAdapter(
            onAddressSelected = { address ->
                viewModel.setAddress(address)
                findNavController().popBackStack()
            },
            onEditClick = { address ->
                val bundle = Bundle().apply {
                    putParcelable("address_data", address)
                }
                findNavController().navigate(R.id.addEditAddressFragment, bundle)
            },
            onDeleteClick = { address ->
                viewModel.deleteAddress(address)
                Toast.makeText(requireContext(), "Address removed", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvAddresses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        viewModel.allAddresses.observe(viewLifecycleOwner) { addresses ->
            adapter.submitList(addresses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}