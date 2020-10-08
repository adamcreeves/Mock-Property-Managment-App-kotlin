package com.example.mockpropertymanagmentapp.ui.otheractivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.ui.auth.activities.LoginActivity
import com.example.mockpropertymanagmentapp.ui.auth.activities.RegisterActivity
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