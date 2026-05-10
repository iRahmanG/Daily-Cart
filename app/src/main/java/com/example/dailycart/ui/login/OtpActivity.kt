package com.example.dailycart.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.R
import com.example.dailycart.databinding.ActivityOtpBinding
import kotlin.jvm.java

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val phoneNumber = intent.getStringExtra("phoneNumber")
        binding.tvOtpSentTo.text = "We've sent a 4-digit code to $phoneNumber. Edit"

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnVerify.setOnClickListener {
            val otp = "${binding.otp1.text}${binding.otp2.text}${binding.otp3.text}${binding.otp4.text}"
            viewModel.verifyOtp(otp)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginViewModel.LoginState.Success -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finishAffinity() // Modern practice for login completion
                }
                is LoginViewModel.LoginState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}