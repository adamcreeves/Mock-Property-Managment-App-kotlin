package com.example.mockpropertymanagmentapp.data.network

import com.example.mockpropertymanagmentapp.app.Config
import com.example.mockpropertymanagmentapp.data.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @POST("auth/login")
    fun login(@Body loginUser: LoginUser) : Call<LoginResponse>

    @POST("auth/register")
    fun registerLandlord(@Body landlord: Landlord) : Call<RegisterResponse>

    @POST("auth/register")
    fun registerTenant(@Body tenant: Tenant) : Call<RegisterResponse>

//    @PUT("")
//    fun updateUser(@Body user: User, @Query("id") id: Int)
//
//    @DELETE("")
//    fun deleteUser(@Path("id") id: Int)

    companion object {
        operator fun invoke() : MyApi {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}