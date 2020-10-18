package com.example.mockpropertymanagmentapp.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mockpropertymanagmentapp.data.models.PropertiesResponse
import com.example.mockpropertymanagmentapp.data.models.Property
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.properties.adapters.AdapterProperties
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyRepository {
    lateinit var sessionManager: SessionManager
    fun getData(myContext: Context, adapterProperties: AdapterProperties) {
        sessionManager = SessionManager(myContext)
        var userId = sessionManager.getUserId()
        MyApi().getUserProperties(userId)
            .enqueue(object: Callback<PropertiesResponse> {
                override fun onResponse(
                    call: Call<PropertiesResponse>,
                    response: Response<PropertiesResponse>
                ) {
                    adapterProperties?.setData(response.body()!!.data as ArrayList<Property>)
                }
                override fun onFailure(call: Call<PropertiesResponse>, t: Throwable) {
                    myContext.toastShort("Something went wrong")
                }
            })
    }
//    fun getUserProperties() : LiveData<ArrayList<Property>> {
//        var propertyResponse = MutableLiveData<ArrayList<Property>>()
//        MyApi().getUserProperties()
//            .enqueue(object: Callback<PropertiesResponse>{
//                override fun onResponse(
//                    call: Call<PropertiesResponse>,
//                    response: Response<PropertiesResponse>
//                ) {
//                    if(response.isSuccessful)
//                        propertyResponse.value = response.body()!!.data as ArrayList<Property>
//                }
//                override fun onFailure(call: Call<PropertiesResponse>, t: Throwable) {
//                    Log.d("abc", t.message.toString())
//                }
//            })
//        return propertyResponse
//    }
}