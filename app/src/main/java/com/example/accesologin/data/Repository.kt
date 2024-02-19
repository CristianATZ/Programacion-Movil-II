package com.example.accesologin.data

import com.example.accesologin.model.Acceso
import com.example.accesologin.model.Alumno
import com.example.accesologin.network.repository.AlumnoInfoService
import com.example.accesologin.network.repository.LoginApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOError
import java.io.IOException

interface Repository {
    suspend fun getAccess(matricula: String, password: String, tipoUsuario: String): Boolean
    suspend fun getInfo(): String
}

class NetworkRepository(
    private val info: AlumnoInfoService,
    private val login: LoginApiService
): Repository{
    override suspend fun getAccess(matricula: String, password: String, tipoUsuario: String) : Boolean{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${matricula}</strMatricula>
                  <strContrasenia>${password}</strContrasenia>
                  <tipoUsuario>${tipoUsuario}</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        login.getCookies()
        return try {
            var response = login.getLogin(requestBody).string().split("{","}")
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
            val respuestaInfo= info.getInfo(requestBody).string().split("{","}")
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                ""+result
            } else
                ""
        }catch (e:IOException){
            ""
        }
    }
}