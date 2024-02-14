package com.example.accesologin.network.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

private const val BASE_URL =
    "https://sicenet.surguanajuato.tecnm.mx/ws/wsalumnos.asmx"

val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(getClient())
    .baseUrl(BASE_URL)
    .build()

private fun getClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor()).build()
}