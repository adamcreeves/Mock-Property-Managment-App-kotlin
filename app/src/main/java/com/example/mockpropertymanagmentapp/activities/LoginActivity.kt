package com.example.mockpropertymanagmentapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.api.MyApi
import com.example.mockpropertymanagmentapp.models.LoginResponse
import com.example.mockpropertymanagmentapp.models.LoginUser
import com.example.mockpropertymanagmentapp.models.RegisterResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        var api = MyApi()
        button_login_submit.setOnClickListener {
            var email = edit_text_login_email.text.toString()
            var password = edit_text_login_password.text.toString()
            var loginUser = LoginUser(email, password)
            api.login(loginUser)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.message() == "Not Found") {
                            Toast.makeText(
                                applicationContext,
                                "Email and/or Password Invalid",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("abc", response.message())
                            Toast.makeText(
                                applicationContext,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    }

                })
        }
        button_login_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}