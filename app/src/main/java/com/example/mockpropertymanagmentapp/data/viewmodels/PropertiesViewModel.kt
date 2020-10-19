package com.example.mockpropertymanagmentapp.data.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mockpropertymanagmentapp.data.listeners.NewPropertyListener
import com.example.mockpropertymanagmentapp.data.repositories.UserRepository
import com.example.mockpropertymanagmentapp.data.listeners.PropertyListListener
import com.example.mockpropertymanagmentapp.data.repositories.PropertyRepository
import com.example.mockpropertymanagmentapp.helpers.toastShort

class PropertiesViewModel : ViewModel() {
    var address: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var purchasePrice: String? = null
    var image: String? =
        "https://i.pinimg.com/736x/1c/8a/9a/1c8a9a658ea0709e698d56389376a28b.jpg"
    var newPropertyListener: NewPropertyListener? = null

    fun onAddPropertyClicked(view: View) {
        newPropertyListener?.onHasStarted()
        if (address.isNullOrEmpty() || city.isNullOrEmpty() || state.isNullOrEmpty() || country.isNullOrEmpty() || purchasePrice.isNullOrEmpty()) {
            newPropertyListener?.onFailedAdd("You need to fill in all of the text fields")
        }
        val propertyResponse = PropertyRepository().addNewProperty(address!!,city!!, country!!, image!!, purchasePrice!!, state!!)
        newPropertyListener?.onSuccessfulAdd(propertyResponse)
    }
}