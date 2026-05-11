package com.example.dailycart.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dailycart.MainActivity
import com.example.dailycart.databinding.ActivityOtpBinding
import com.example.dailycart.utils.SessionManager

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager // Initialize SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this) // Instance creation
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        binding.tvOtpSentTo.text = "We've sent a 4-digit code to $phoneNumber. Edit"

        setupListeners()
        observeViewModel(phoneNumber)
    }

    private fun setupListeners() {
        binding.btnVerify.setOnClickListener {
            val otp = "${binding.otp1.text}${binding.otp2.text}${binding.otp3.text}${binding.otp4.text}"
            if (otp.length == 4) {
                viewModel.verifyOtp(otp)
            } else {
                Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel(phoneNumber: String) {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginViewModel.LoginState.Success -> {
                    sessionManager.saveLoginSession(phoneNumber)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
                is LoginViewModel.LoginState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}