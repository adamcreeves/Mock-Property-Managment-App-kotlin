package com.example.mockpropertymanagmentapp.data.repositories


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mockpropertymanagmentapp.data.models.*
import com.example.mockpropertymanagmentapp.data.network.MyApi
import com.example.mockpropertymanagmentapp.helpers.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserRepository {
    fun login(email: String, password: String): LiveData<String> {
        var loginResponse = MutableLiveData<String>()
        var loginUser = LoginUser(email, password)
        MyApi().login(loginUser)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()!!.token
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
    @SuppressLint("CheckResult")
    fun getProperties() {
        MyApi().getProperties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<ResponseBody>(){
                override fun onError(e: Throwable) {
                    Log.d("abc", "Get Properties did not work")
                }

                override fun onSuccess(t: ResponseBody) {
                    Log.d("abc", "Get Properties successful")
                }

            })
    }
    fun postNewImage(path: String) {
        var file = File(path)
        var requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        var body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        MyApi().postNewImage(body)
    }


}