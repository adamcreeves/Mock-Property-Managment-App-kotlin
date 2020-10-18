package com.example.mockpropertymanagmentapp.data.listeners

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(response: LiveData<String>)
    fun onFailure(message: String)
}