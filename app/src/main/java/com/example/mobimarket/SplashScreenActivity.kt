package com.example.mobimarket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mobimarket.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val SPLASH_TIME_OUT: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}