package com.example.mockpropertymanagmentapp.api

import com.example.mockpropertymanagmentapp.models.Landlord
import com.example.mockpropertymanagmentapp.models.RegisterResponse
import com.example.mockpropertymanagmentapp.models.Tenant
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

//    @POST("/api/auth/login")
//    fun login(@Body tenantOrLandlord: TenantOrLandlord) : Call<LoginResponse>

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
                .baseUrl("https://apolis-property-management.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}