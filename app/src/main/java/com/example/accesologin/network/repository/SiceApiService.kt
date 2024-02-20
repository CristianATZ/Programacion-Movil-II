package com.example.accesologin.network.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SiceApiService {
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/accesoLogin\""
    )

    @POST("ws/wsalumnos.asmx")
    suspend fun getAccess(
        @Body requestBody: RequestBody
    ): ResponseBody

    @GET("ws/wsalumnos.asmx")
    suspend fun getCookies(): ResponseBody
}