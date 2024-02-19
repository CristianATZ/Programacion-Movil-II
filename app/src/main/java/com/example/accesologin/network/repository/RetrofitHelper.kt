package com.example.accesologin.network.repository

import com.example.accesologin.network.Interceptors.AddCookiesInterceptor
import com.example.accesologin.network.Interceptors.ReceivedCookiesInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/*
private val builder = OkHttpClient.Builder()
    */
/*.addInterceptor(AddCookiesInterceptor())
    .addInterceptor(ReceivedCookiesInterceptor())*//*

    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    //.client(builder)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()*/
