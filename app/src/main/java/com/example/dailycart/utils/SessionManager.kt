package com.example.dailycart.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
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

    fun logout() {
        prefs.edit().clear().apply()
    }
}