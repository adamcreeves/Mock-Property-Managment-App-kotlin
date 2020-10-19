package com.example.mockpropertymanagmentapp.data.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mockpropertymanagmentapp.data.repositories.UserRepository
import com.example.mockpropertymanagmentapp.data.listeners.AuthListener

class AuthViewModel : ViewModel() {
    var email: String? = null
    var landlordEmail: String? = null
    var name: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var type: String? = null
    var authListener: AuthListener? = null

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Incorrect Email or Password")
        }
        val loginResponse = UserRepository().login(view.context, email!!, password!!)
        authListener?.onSuccess(loginResponse)
    }
    fun onRegisterTenantClicked(view: View) {
        authListener?.onStarted()
        if(password.isNullOrEmpty() || password != confirmPassword) {
            authListener?.onFailure("Registration unsuccessful")
        }
        val registerResponse = UserRepository().registerTenant(email!!, landlordEmail!!, name!!, password!!, type!!)
        authListener?.onSuccess(registerResponse)
    }
    fun onRegisterLandlordClicked(view: View) {
        authListener?.onStarted()
        if(password.isNullOrEmpty() || password != confirmPassword) {
            authListener?.onFailure("Registration unsuccessful")
        }
        val registerResponse = UserRepository().registerLandlord(email!!, name!!, password!!, type!!)
        authListener?.onSuccess(registerResponse)
    }
}