package com.example.mockpropertymanagmentapp.data.repositories

import android.content.Context
import android.view.View
import com.example.mockpropertymanagmentapp.data.models.PropertiesResponse
import com.example.mockpropertymanagmentapp.data.models.Property
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.properties.adapters.AdapterProperties
import kotlinx.android.synthetic.main.activity_property.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyRepository {
    fun getData(myContext: Context, adapterProperties: AdapterProperties) {
        MyApi().getUserProperties()
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

}