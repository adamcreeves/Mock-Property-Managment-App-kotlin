package com.example.mockpropertymanagmentapp.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mockpropertymanagmentapp.data.models.LoginResponse
import com.example.mockpropertymanagmentapp.data.models.LoginUser
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun login(email: String, password: String) : LiveData<String> {

        var loginResponse = MutableLiveData<String>()
        var loginUser = LoginUser(email, password)
        MyApi().login(loginUser)
            .enqueue(object: Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful) {
                        loginResponse.value = response.body()!!.token
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResponse.value = t.message
                }
            })
        return loginResponse
    }
}