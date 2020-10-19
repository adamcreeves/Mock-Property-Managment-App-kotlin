package com.example.mockpropertymanagmentapp.data.listeners

import androidx.lifecycle.LiveData

interface ToDoListener {
    fun onStarted()
    fun onSuccessfulAdd(message: String)
    fun onFailedAdd(message: String)
}