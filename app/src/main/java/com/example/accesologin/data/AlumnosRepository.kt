package com.example.accesologin.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Acceso
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno
import com.example.accesologin.model.CalifFinal
import com.example.accesologin.model.CalificacionByUnidad
import com.example.accesologin.model.Cardex
import com.example.accesologin.model.CardexProm
import com.example.accesologin.model.Carga
import com.example.accesologin.network.repository.AcademicScheduleService
import com.example.accesologin.network.repository.CalifFinalesService
import com.example.accesologin.network.repository.CalificacionesService
import com.example.accesologin.network.repository.InfoService
import com.example.accesologin.network.repository.KardexService
import com.example.accesologin.network.repository.SiceApiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

interface AlumnosRepository {
    suspend fun obtenerAcceso(matricula: String, password: String): Boolean
    suspend fun obtenerInfo(): String
    suspend fun obtenerCarga(): String
    suspend fun obtenerCalificaciones() : String
    suspend fun obtenerCalifFinales(): String
    suspend fun obtenerCardex() : String
}

class NetworkAlumnosRepository(
    private val alumnoApiService: SiceApiService,
    private val infoService: InfoService,
    private val academicScheduleService: AcademicScheduleService,
    private val calificacionesService: CalificacionesService,
    private val califFinales: CalifFinalesService,
    private val alumnoCardex: KardexService
): AlumnosRepository {
    @RequiresApi(Build.VERSION_CODES.O)
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
        try {
            var respuesta=alumnoApiService.getAccess(requestBody).string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}", Acceso::class.java)
                if(result.acceso.equals("true")){
                    CoroutineScope(Dispatchers.IO).launch {
                        AlumnosContainer.getUserLoginDao().deleteAccesos()
                        AlumnosContainer.getUserLoginDao().insertAcceso(
                            Acceso_Entity(
                                id = 0,
                                acceso = result.acceso,
                                estatus = result.estatus,
                                contrasenia = password,
                                matricula = matricula,
                                fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                            )
                        )
                    }
                    return true
                }
                else return false
            } else {
                return false
            }
        }catch (e:IOException){
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                return "" + result
            } else
                return ""
        }catch (e:IOException){
            return ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun obtenerCarga(): String {
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
                return ""+arreglo
            } else
                return ""
             return ""
        } catch (e: IOException){
            return ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun obtenerCalificaciones(): String {
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
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<CalificacionByUnidad>()
                for(calificaciones in respuestaInfo){
                    if(calificaciones.contains("Materia")){
                        val objCalif = Gson().fromJson("{"+calificaciones+"}", CalificacionByUnidad::class.java)
                        arreglo.add(objCalif)
                    }
                }
                return ""+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun obtenerCalifFinales(): String {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
                  <bytModEducativo>2</bytModEducativo>
                </getAllCalifFinalByAlumnos>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = califFinales.getAllCalifFinalByAlumnos(requestBody).string().split("{","}")
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<CalifFinal>()
                for(calificaciones in respuestaInfo){
                    if(calificaciones.contains("calif")){
                        val objCalif = Gson().fromJson("{"+calificaciones+"}", CalifFinal::class.java)
                        arreglo.add(objCalif)
                    }
                }
                return ""+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun obtenerCardex(): String {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
                        <aluLineamiento>2</aluLineamiento>
                    </getAllKardexConPromedioByAlumno>
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = alumnoCardex.getCardex(requestBody).string().split("{","}")
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<Cardex>()
                var prom: String = "Null"
                for(cardex in respuestaInfo){
                    if(cardex.contains("Materia")){
                        val objCardex = Gson().fromJson("{$cardex}", Cardex::class.java)
                        arreglo.add(objCardex)
                    } else if(cardex.contains("PromedioGral")){
                        prom = Gson().fromJson("{$cardex}", CardexProm::class.java).toString()
                        var objProm = Gson().fromJson("{$cardex}", CardexProm::class.java)
                    }
                }
                return prom+"~"+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }


}