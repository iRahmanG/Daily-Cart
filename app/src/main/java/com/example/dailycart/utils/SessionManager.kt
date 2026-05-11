package com.example.dailycart.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.dailycart.ui.login.LoginActivity

class SessionManager(private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("daily_cart_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val USER_PHONE = "user_phone"
    }

    fun saveLoginSession(phoneNumber: String) {
        prefs.edit().apply {
            putBoolean(IS_LOGGED_IN, true)
            putString(USER_PHONE, phoneNumber)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(IS_LOGGED_IN, false)

    fun triggerReAuthentication(context: Context) {
        logout()
        val intent = Intent(context, com.example.dailycart.ui.login.LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("reauth_required", true)
        }
        context.startActivity(intent)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}