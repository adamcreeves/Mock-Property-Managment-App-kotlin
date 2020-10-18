package com.example.mockpropertymanagmentapp.data.network

import com.example.mockpropertymanagmentapp.app.Config
import com.example.mockpropertymanagmentapp.data.models.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.MultipartBody
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

    @POST("property")
    fun addProperty(@Body property: Prop) : Call<AddPropertyResponse>

//    @PUT("")
//    fun updateUser(@Body user: User, @Query("id") id: Int)
//
//    @DELETE("")
//    fun deleteUser(@Path("id") id: Int)

    @Multipart
    @POST("upload/property/picture")
    fun postNewImage(
        @Part image: MultipartBody.Part
    ) : Call<UploadPictureResponse>

@GET("property/user/{userId}" )
fun getUserProperties(@Path("userId") userId: String) : Call<PropertiesResponse>

    companion object {
        operator fun invoke() : MyApi {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(ApiMiddleman.client)
                .build()
                .create(MyApi::class.java)
        }
    }
}