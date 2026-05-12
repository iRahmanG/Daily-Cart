package com.example.dailycart.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dailycart.MainActivity
import com.example.dailycart.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = ContextCompat.getColor(this, R.color.secondary)

        val ivLogo = findViewById<ImageView>(R.id.ivLogo)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        ivLogo.startAnimation(fadeIn)

        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2500)
    }
}