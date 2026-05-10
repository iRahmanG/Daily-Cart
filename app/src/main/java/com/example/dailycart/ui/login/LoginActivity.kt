package com.example.dailycart.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.R
import com.example.dailycart.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupListeners()
        observeViewModel()

    }
    private fun setupListeners(){
        binding.btnGetOtp.setOnClickListener {
            val phoneNumber = binding.etPhoneNumber.text.toString()
            viewModel.validatePhoneNumber(phoneNumber)
        }
    }
    private fun observeViewModel(){
        viewModel.loginState.observe(this){ state ->
            when(state) {
                is LoginViewModel.LoginState.NavigateToOtp -> {
                    val intent = Intent(this, OtpActivity::class.java).apply {
                        putExtra("phoneNumber", state.phoneNumber)
                    }
                    startActivity(intent)
                }

                is LoginViewModel.LoginState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()

                }
                else -> {}
            }

        }
    }
}