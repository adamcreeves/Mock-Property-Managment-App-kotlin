package com.example.mockpropertymanagmentapp.ui.auth.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.data.models.LoginResponse
import com.example.mockpropertymanagmentapp.data.models.LoginUser
import com.example.mockpropertymanagmentapp.databinding.ActivityLoginBinding
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.auth.AuthListener
import com.example.mockpropertymanagmentapp.ui.auth.AuthViewModel
import com.example.mockpropertymanagmentapp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), AuthListener {
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        button_login_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        button_login_reset_password.setOnClickListener{
            Toast.makeText(this, "You can't reset your password right now", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStarted() {
        this.toastShort("Login started")
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            this.toastShort("Login successfull")
            Log.d("cbd", response.value.toString())
//            sessionManager.saveUserLogin(response.value.toString())
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        })

    }

    override fun onFailure(message: String) {
        this.toastShort(message)
    }
}