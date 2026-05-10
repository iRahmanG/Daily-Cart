package com.example.dailycart.ui.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {

    // LiveData to handle navigation and error states
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun validatePhoneNumber(phone: String) {
        if (phone.length == 10 && phone.all { it.isDigit() }) {
            _loginState.value = LoginState.NavigateToOtp(phone)
        } else {
            _loginState.value = LoginState.Error("Please enter a valid 10-digit number")
        }
    }

    fun verifyOtp(otp: String) {
        // Requirement: Fake OTP 1234
        if (otp == "1234") {
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error("Invalid OTP. Hint: 1234")
        }
    }

    // Sealed class for state management
    sealed class LoginState {
        data class NavigateToOtp(val phoneNumber: String) : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}