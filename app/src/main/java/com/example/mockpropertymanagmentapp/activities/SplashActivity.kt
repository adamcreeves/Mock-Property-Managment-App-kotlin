package com.example.mockpropertymanagmentapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.helpers.SessionManager

class SplashActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    private val delay: Long = 2400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        var handler = Handler()
        if (sessionManager.getQuickLogin())
            handler.postDelayed({
                startActivity(Intent(this, HomeActivity::class.java))
            }, delay)
        else
            handler.postDelayed({
                startActivity(Intent(this, StartActivity::class.java))
            }, delay)
    }
}