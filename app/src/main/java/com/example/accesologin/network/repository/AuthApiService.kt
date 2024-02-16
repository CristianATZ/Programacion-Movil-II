package com.example.accesologin.network.repository

import android.telecom.Call
import com.example.accesologin.model.AccesoLoginRequest
import com.example.accesologin.model.AccesoLoginResponse
import com.example.accesologin.model.SoapEnvelope
import com.example.accesologin.network.dto.LoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

val bodyAcceso = ""

interface LoginApiService {
    @FormUrlEncoded
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun accesoLogin(@Body request: SoapEnvelope<AccesoLoginRequest>): Response<SoapEnvelope<AccesoLoginResponse>>
}


object LoginApi {
    val retrofitService : LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}