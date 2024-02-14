package com.example.accesologin.network

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


data class LoginDto(
    @SerializedName("strMatricula") val email: String,
    @SerializedName("strContrasenia") val password: String,
    @SerializedName("tipoUsuario") val tipoUsuario: String
)
data class TokenDto(
    @SerializedName("accessToken") val accessTokenVerify: String
)

private const val BASE_URL =
    "https://sicenet.surguanajuato.tecnm.mx/ws/wsalumnos.asmx"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService {
    @FormUrlEncoded
    @Headers(
        "Content-Type: text/xml"
    )
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