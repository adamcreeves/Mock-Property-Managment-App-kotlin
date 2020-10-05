package com.example.mockpropertymanagmentapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockpropertymanagmentapp.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
    }

    private fun init() {
        button_start_login.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        button_start_sign_up.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}