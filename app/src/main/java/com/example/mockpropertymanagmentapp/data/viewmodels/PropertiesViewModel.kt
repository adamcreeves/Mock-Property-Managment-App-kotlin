package com.example.mockpropertymanagmentapp.data.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mockpropertymanagmentapp.data.repositories.UserRepository
import com.example.mockpropertymanagmentapp.data.listeners.PropertyListListener

class PropertiesViewModel : ViewModel() {
    var address: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var purchasePrice: String? = null
    var propertyListListener: PropertyListListener? = null
//    fun onGetPropertiesClicked(view: View) {
//        Log.d("abc", "Properties are being retrieved")
//        var propertiesResponse = UserRepository().getUserProperties()
//        propertyListListener?.onSuccessful(propertiesResponse)
//        if(propertiesResponse == null) {
//            propertyListListener?.onFailed("Properties were not retrieved")
//        }
//    }

}