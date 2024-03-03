package com.example.accesologin.data

import android.util.Log
import com.example.accesologin.model.Acceso
import com.example.accesologin.model.Alumno
import com.example.accesologin.model.CalificacionByUnidad
import com.example.accesologin.model.Carga
import com.example.accesologin.network.repository.AcademicScheduleService
import com.example.accesologin.network.repository.CalificacionesService
import com.example.accesologin.network.repository.InfoService
import com.example.accesologin.network.repository.SiceApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

interface AlumnosRepository {
    suspend fun obtenerAcceso(matricula: String, password: String): Boolean
    suspend fun obtenerInfo(): String
    suspend fun obtenerCarga(): String
    suspend fun obtenerCalificaciones() : String
}

class NetworkAlumnosRepository(
    private val alumnoApiService: SiceApiService,
    private val infoService: InfoService,
    private val academicScheduleService: AcademicScheduleService,
    private val calificacionesService: CalificacionesService
): AlumnosRepository {
    override suspend fun obtenerAcceso(matricula: String, password: String): Boolean {
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
        val TAG = "REPOSITORY"
        try {
            var respuesta=alumnoApiService.getAccess(requestBody).string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}", Acceso::class.java)
                Log.d(TAG, "ENTRO AL IF Y ES: " + result.acceso.toString())
                return result.acceso.equals("true")
            } else {
                Log.d(TAG, "ENTRO AL ELSE Y ES: false")
                return false
            }
        }catch (e:IOException){
            Log.d(TAG, "ENTRO AL EXCEPTION Y ES: false")
            return false
        }
    }

    override suspend fun obtenerInfo() : String{
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
                //Log.d("REPOSITORY", respuestaInfo[1])
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                return "" + result
            } else
                return ""
        }catch (e:IOException){
            return ""
        }
    }

    override suspend fun obtenerCarga(): String {
        val TAG = "REPOSITORY"
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = academicScheduleService.getAcademicSchedule(requestBody).string().split("{","}")
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<Carga>()
                for(carga in respuestaInfo){
                    if(carga.contains("Materia")){
                        val objCarga = Gson().fromJson("{"+carga+"}", Carga::class.java)
                        arreglo.add(objCarga)
                    }
                }
                Log.d(TAG, arreglo.toString())
                return ""+arreglo
            } else
                return ""
             return ""
        } catch (e: IOException){
            return ""
        }
    }

    override suspend fun obtenerCalificaciones(): String {
        val TAG = "REPOSITORY"
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = calificacionesService.getCalifUnidadesByAlumno(requestBody).string().split("{","}")
            //Log.d("asdasd", respuestaInfo.toString())
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<CalificacionByUnidad>()
                for(calificaciones in respuestaInfo){
                    if(calificaciones.contains("Materia")){
                        val objCalif = Gson().fromJson("{"+calificaciones+"}", CalificacionByUnidad::class.java)
                        Log.d("asdasd", objCalif.toString())
                        arreglo.add(objCalif)
                    }
                }
                //Log.d("asdasd", arreglo.toString())
                return ""+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }
}