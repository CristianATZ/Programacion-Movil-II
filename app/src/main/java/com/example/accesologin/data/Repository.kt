package com.example.accesologin.data

import com.example.accesologin.model.Acceso
import com.example.accesologin.model.Alumno
import com.example.accesologin.network.repository.AlumnoInfoService
import com.example.accesologin.network.repository.InfoService
import com.example.accesologin.network.repository.LoginApiService
import com.example.accesologin.network.repository.SiceApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOError
import java.io.IOException

interface Repository {
    suspend fun getAccess(matricula: String, password: String): Boolean
    suspend fun getInfo(): String
}

class NetworkRepository(
    private val alumnoApiService: SiceApiService,
    private val infoService: InfoService
): Repository{
    override suspend fun getAccess(matricula: String, password: String) : Boolean{
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
        return try {
            var response = alumnoApiService.getAccess(requestBody).string().split("{","}")
            if(response.size > 1){
                val result = Gson().fromJson("{" + response[1] + "}", Acceso::class.java)
                result.acceso.equals("true")
            } else false
        } catch (e: IOException){
            false
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
        return try {
            val respuestaInfo= infoService.getInfo(requestBody).string().split("{","}")
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                "" + result
            } else
                ""
        }catch (e:IOException){
            ""
        }
    }
}