package com.example.accesologin.data

import com.example.accesologin.network.repository.AcademicScheduleService
import com.example.accesologin.network.repository.CalifFinalesService
import com.example.accesologin.network.repository.CalificacionesService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import com.example.accesologin.network.repository.InfoService
import com.example.accesologin.network.repository.KardexService
import com.example.accesologin.network.repository.SiceApiService

interface AppContainer {
    val alumnosRepository: AlumnosRepository
}

class DefaultAppContainer: AppContainer {
    private val BASE_URL =
        "https://sicenet.surguanajuato.tecnm.mx/"

    private val interceptor= CookiesInterceptor()

    private val cliente = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .baseUrl(BASE_URL).client(cliente)
        .build()

    private val retrofitService : SiceApiService by lazy {
        retrofit.create(SiceApiService::class.java)
    }

    private val retrofitServiceInfo : InfoService by lazy {
        retrofit.create(InfoService::class.java)
    }

    private val retrofitAcademicScheduleService : AcademicScheduleService by lazy {
        retrofit.create(AcademicScheduleService::class.java)
    }

    private val retrofitCalificacionesByUnidad : CalificacionesService by lazy {
        retrofit.create(CalificacionesService::class.java)
    }

    private val retrofitCalifFinals: CalifFinalesService by lazy {
        retrofit.create(CalifFinalesService::class.java)
    }

    private val retrofitKardexConPromedioByAlumno: KardexService by lazy {
        retrofit.create(KardexService::class.java)
    }

    override val alumnosRepository: AlumnosRepository by lazy {
        NetworkAlumnosRepository(
            retrofitService,
            retrofitServiceInfo,
            retrofitAcademicScheduleService,
            retrofitCalificacionesByUnidad,
            retrofitCalifFinals,
            retrofitKardexConPromedioByAlumno
        )
    }
}


class CookiesInterceptor : Interceptor {
    // Map para guardar la cookie
    private val cookieStore = mutableMapOf<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Agregar la cookie al encabezado de la solicitud
        val cookiesHeader = StringBuilder()
        for ((name, value) in cookieStore) {
            if (cookiesHeader.isNotEmpty()) {
                cookiesHeader.append("; ")
            }
            cookiesHeader.append("$name=$value")
        }

        if (cookiesHeader.isNotEmpty()) {
            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }
        val response = chain.proceed(request)

        // Almacenar la cookiea para futuras solicitudes
        val receivedCookies = response.headers("Set-Cookie")
        for (cookie in receivedCookies) {
            val parts = cookie.split(";")[0].split("=")
            if (parts.size == 2) {
                val name = parts[0]
                val value = parts[1]
                cookieStore[name] = value
            }
        }

        return response
    }
}