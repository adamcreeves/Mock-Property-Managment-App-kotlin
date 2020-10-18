package com.example.mockpropertymanagmentapp.data.repositories


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mockpropertymanagmentapp.data.models.*
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserRepository {
    lateinit var sessionManager: SessionManager
    var userId: String? = null
    fun login(myContext: Context, email: String, password: String): LiveData<String> {
        var loginResponse = MutableLiveData<String>()
        var loginUser = LoginUser(email, password)
        MyApi().login(loginUser)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        sessionManager = SessionManager(myContext)
                        loginResponse.value = response.body()!!.token
                        sessionManager.saveUserLogin(response.body()!!.token, response.body()!!.user._id)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResponse.value = t.message
                }
            })
        return loginResponse
    }

    fun registerTenant(
        email: String,
        landlordEmail: String,
        name: String,
        password: String,
        type: String
    ): LiveData<String> {
        var registerResponse = MutableLiveData<String>()
        var registerTenant = Tenant(email, landlordEmail, name, password, type)
        MyApi().registerTenant(registerTenant)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        registerResponse.value = "Registered Tenant successful"
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registerResponse.value = t.message
                }
            })
        return registerResponse
    }

    fun registerLandlord(
        email: String,
        name: String,
        password: String,
        type: String
    ): LiveData<String> {
        var registerResponse = MutableLiveData<String>()
        var registerLandlord = Landlord(email, name, password, type)
        MyApi().registerLandlord(registerLandlord)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if(response.isSuccessful){
                        registerResponse.value = "Registered Landlord successful"

                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registerResponse.value = t.message
                }
            })
        return registerResponse
    }

    fun getUserProperties() : LiveData<ArrayList<Property>> {
        var propertyResponse = MutableLiveData<ArrayList<Property>>()
        MyApi().getUserProperties()
            .enqueue(object: Callback<PropertiesResponse>{
                override fun onResponse(
                    call: Call<PropertiesResponse>,
                    response: Response<PropertiesResponse>
                ) {
                    if(response.isSuccessful)
                        propertyResponse.value = response.body()!!.data as ArrayList<Property>
                }
                override fun onFailure(call: Call<PropertiesResponse>, t: Throwable) {
                    Log.d("abc", t.message.toString())
                }
            })
        return propertyResponse
    }
}