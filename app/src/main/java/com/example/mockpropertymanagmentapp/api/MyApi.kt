package com.example.mockpropertymanagmentapp.api

import com.example.mockpropertymanagmentapp.models.LoginResponse
import com.example.mockpropertymanagmentapp.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @POST("/api/auth/login")
    fun login(@Body user: User) : Call<LoginResponse>

    @POST("/api/auth/register")
    fun register()

//    @PUT("")
//    fun updateUser(@Body user: User, @Query("id") id: Int)
//
//    @DELETE("")
//    fun deleteUser(@Path("id") id: Int)

    companion object {
        operator fun invoke() : MyApi {
            return Retrofit.Builder()
                .baseUrl("https://apolis-property-management.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}