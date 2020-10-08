package com.example.mockpropertymanagmentapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mockpropertymanagmentapp.data.repositories.UserRepository

class AuthViewModel : ViewModel() {
    var email: String? = null
//    var landlordEmail: String? = null
//    var name: String? = null
    var password: String? = null
//    var type: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Login unsuccessful")
        }
        val loginResponse = UserRepository().login(email!!, password!!)
        authListener?.onSuccess(loginResponse)
    }

}