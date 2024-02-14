package com.example.accesologin.network.repository

import com.example.accesologin.network.dto.LoginDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LoginApiService {
    @FormUrlEncoded
    @POST("accesoLogin")
    suspend fun getAlumno(
        @Body loginDto: LoginDto
    ) : String
}


object LoginApi {
    val retrofitService : LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}