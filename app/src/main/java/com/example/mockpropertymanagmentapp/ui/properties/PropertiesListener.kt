package com.example.mockpropertymanagmentapp.ui.properties

import androidx.lifecycle.LiveData
import com.example.mockpropertymanagmentapp.data.models.Property

interface PropertiesListener {
    fun onStarted()
    fun onSuccessful(response: LiveData<String>)
    fun onFailure(message: String)
}