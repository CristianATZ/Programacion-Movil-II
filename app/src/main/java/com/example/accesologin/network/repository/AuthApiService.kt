package com.example.accesologin.network.repository

import com.example.accesologin.model.AccesoLoginRequest
import com.example.accesologin.model.AccesoLoginResponse
import com.example.accesologin.model.SoapEnvelope
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL =
    "https://sicenet.surguanajuato.tecnm.mx"

const val bodyAcceso = """
                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                    xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                  <soap:Body>
                    <accesoLogin xmlns="http://tempuri.org/">
                      <strMatricula>%s</strMatricula>
                      <strContrasenia>%s</strContrasenia>
                      <tipoUsuario>ALUMNO</tipoUsuario>
                    </accesoLogin>
                  </soap:Body>
                </soap:Envelope>
            """

const val bodyPerfil = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
                            </soap:Body>
                        </soap:Envelope>"""

interface LoginApiService {
    // autenticacion
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/accesoLogin\"",
        //"Cookie: <Insertar cookie>"
    )
    @POST("/ws/wsalumnos.asmx")
    fun getLogin(@Body requestBody: RequestBody): ResponseBody

    @GET("ws/wsalumnos.asmx")
    fun getCookies(): ResponseBody
}

interface AlumnoInfoService {
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getAlumnoAcademicoWithLineamiento\"",
        //"Cookie: .ASPXANONYMOUS=Ep4u2XmY2gEkAAAAOWQ1NzE0ZjMtNDBjZi00NjVmLWJjNDEtYmE3MTIwMmE3ZDgwq__VynMXe9_0bf2Sns0hO3CtLws1"
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun getInfo(
        @Body requestBody: RequestBody
    ): ResponseBody
}


object LoginServiceFactory {
    // Implementación de CookieJar para manejar cookies
    private val cookieJar = object : CookieJar {
        private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies.toMutableList()
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookieStore[url.host] ?: ArrayList()
        }
    }
    // Configuración del OkHttpClient con persistencia de cookies
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        /*.addInterceptor(AddCookiesInterceptor())
    .addInterceptor(ReceivedCookiesInterceptor())*/
        .build()

    // Creación de la instancia de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    // Creación de la instancia de SICENETService
    val retrofitService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}
