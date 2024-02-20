package com.example.accesologin.data

import android.util.Log
import com.example.accesologin.model.Acceso
import com.example.accesologin.model.Alumno
import com.example.accesologin.network.repository.InfoService
import com.example.accesologin.network.repository.SiceApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

interface AlumnosRepository {
    suspend fun getAccess(matricula: String, password: String): Boolean
    suspend fun getInfo(): String
}

class NetworkAlumnosRepository(
    private val alumnoApiService: SiceApiService,
    private val infoService: InfoService
): AlumnosRepository {
    override suspend fun getAccess(matricula: String, password: String): Boolean {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${matricula}</strMatricula>
                  <strContrasenia>${password}</strContrasenia>
                  <tipoUsuario>ALUMNO</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        alumnoApiService.getCookies()
        try {
            var respuesta=alumnoApiService.getAccess(requestBody).string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}", Acceso::class.java)
                //val TAG = "REPOSITORY"
                //Log.d(TAG, "ENTRO AL IF Y ES: " + result.acceso.toString())
                return result.acceso.equals("true")
            } else {
                return false
            }
        }catch (e:IOException){
            return false
        }
    }

    override suspend fun getInfo() : String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {
            val respuestaInfo= infoService.getInfo(requestBody).string().split("{","}")
            //Log.d(TAG, respuestaInfo.toString())
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                return "" + result
            } else
                return ""
        }catch (e:IOException){
            return ""
        }
    }
}