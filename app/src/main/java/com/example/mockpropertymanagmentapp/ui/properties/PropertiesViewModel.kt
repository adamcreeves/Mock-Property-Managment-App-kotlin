package com.example.mockpropertymanagmentapp.ui.properties

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mockpropertymanagmentapp.data.models.User
import com.example.mockpropertymanagmentapp.data.repositories.UserRepository

class PropertiesViewModel : ViewModel() {
    var address: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var purchasePrice: String? = null
    var propertiesListener: PropertiesListener? = null
//    fun onGetPropertiesClicked(view: View) {
//        Log.d("abc", "Properties are being retrieved")
//        var propertiesResponse = UserRepository().getUserProperties()
//        propertiesListener?.onSuccessful(propertiesResponse)
//        if(propertiesResponse == null) {
//            propertiesListener?.onFailure("Properties were not retrieved")
//        }
//    }
    fun onAddPropertyClicked(view: View) {
        Log.d("abc", "Property is being added")
        var propertiesResponse = UserRepository().addNewProperty(address!!, city!!, state!!, country!!, purchasePrice!!)
        propertiesListener?.onSuccessful(propertiesResponse)
    }
}