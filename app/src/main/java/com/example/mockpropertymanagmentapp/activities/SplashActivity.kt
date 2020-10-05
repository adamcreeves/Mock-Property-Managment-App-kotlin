package com.example.mockpropertymanagmentapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mockpropertymanagmentapp.R

class SplashActivity : AppCompatActivity() {
    private val delay: Long = 2400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, StartActivity::class.java))
        }, delay)
    }
}