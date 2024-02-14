package com.example.accesologin.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"
    //"https://sicenet.surguanajuato.tecnm.mx/ws/wsalumnos.asmx/accesoLogin?strMatricula=s20120154&strContrasenia=8s_RH-&tipoUsuario=0"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService {
    @GET("photos")
    suspend fun getAlumno(): String
}

object LoginApi {
    val retrofitService : LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}