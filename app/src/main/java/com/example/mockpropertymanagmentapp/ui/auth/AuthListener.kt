package com.example.mockpropertymanagmentapp.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(response: LiveData<String>)
    fun onFailure(message: String)
}