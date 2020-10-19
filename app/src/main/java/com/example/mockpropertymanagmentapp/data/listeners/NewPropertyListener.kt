package com.example.mockpropertymanagmentapp.data.listeners

import androidx.lifecycle.LiveData
import com.example.mockpropertymanagmentapp.data.models.AddPropertyResponse

interface NewPropertyListener {
    fun onHasStarted()
    fun onSuccessfulAdd(response: LiveData<AddPropertyResponse>)
    fun onFailedAdd(message: String)
}