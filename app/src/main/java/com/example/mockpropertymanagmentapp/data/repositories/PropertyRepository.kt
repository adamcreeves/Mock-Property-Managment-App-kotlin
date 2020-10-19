package com.example.mockpropertymanagmentapp.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mockpropertymanagmentapp.data.models.*
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.properties.adapters.AdapterProperties
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class PropertyRepository {
    lateinit var sessionManager: SessionManager
    fun getData(myContext: Context, adapterProperties: AdapterProperties) {
        sessionManager = SessionManager(myContext)
        var userId = sessionManager.getUserId()
        MyApi().getUserProperties(userId)
            .enqueue(object : Callback<PropertiesResponse> {
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

    @SuppressLint("CheckResult")
    fun addNewProperty(
        address: String,
        city: String,
        country: String,
        image: String,
        purchasePrice: String,
        state: String
    ): LiveData<AddPropertyResponse> {
        val addPropertyResponse = MutableLiveData<AddPropertyResponse>()
        MyApi().addNewProperty(
            Prop(
                address = address,
                city = city,
                country = country,
                purchasePrice = purchasePrice,
                image = image,
                state = state
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<AddPropertyResponse>() {
                override fun onSuccess(t: AddPropertyResponse) {
                    addPropertyResponse.value = t
                }

                override fun onError(e: Throwable) {
                    Log.d("abc", e.toString())
                }
            })
        return addPropertyResponse
    }
}