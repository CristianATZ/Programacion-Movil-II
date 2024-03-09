package com.example.accesologin.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Acceso
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno
import com.example.accesologin.model.Alumno_Entity
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
import java.time.LocalDateTime

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
        val TAG = "REPOSITORY"
        try {
            var respuesta=alumnoApiService.getAccess(requestBody).string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}", Acceso::class.java)
                //Log.d(TAG, "ENTRO AL IF Y ES: " + result.acceso.toString())
                if(result.acceso.equals("true")){
                    CoroutineScope(Dispatchers.IO).launch {
                        AlumnosContainer.getUserLoginDao().insertAcceso(
                            Acceso_Entity(
                                id = 0,
                                acceso = result.acceso,
                                estatus = result.estatus,
                                password = password,
                                matricula = matricula,
                                fecha = LocalDateTime.now().toString()
                            )
                        )
                    }
                    return true
                }
                else return false
            } else {
                //Log.d(TAG, "ENTRO AL ELSE Y ES: false")
                return false
            }
        }catch (e:IOException){
            //Log.d(TAG, "ENTRO AL EXCEPTION Y ES: false")
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
            //Log.d(TAG, respuestaInfo.toString())
            if(respuestaInfo.size>1){
                //Log.d("REPOSITORY", respuestaInfo[1])
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                CoroutineScope(Dispatchers.IO).launch {
                    AlumnosContainer.getUserInfoDao().insertAlumno(
                        Alumno_Entity(
                            id = 0,
                            nombre = result.nombre,
                            fechaReins = result.fechaReins,
                            semActual = result.semActual,
                            cdtosActuales = result.cdtosActuales,
                            cdtosAcumulados = result.cdtosAcumulados,
                            carrera = result.carrera,
                            matricula = result.matricula,
                            especialidad = result.especialidad,
                            modEducativo = result.modEducativo,
                            adeudo = result.adeudo,
                            urlFoto = result.urlFoto,
                            adeudoDescription = result.adeudoDescripcion,
                            inscrito = result.inscrito,
                            estatus = result.estatus,
                            lineamiento = result.lineamiento,
                            fecha = LocalDateTime.now().toString()
                        )
                    )
                }
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
            // val respuestaInfo = calificacionesService.getCalifUnidadesByAlumno(requestBody).string().split("{","}")
            val respuestaInfo = califFinales.getAllCalifFinalByAlumnos(requestBody).string().split("{","}")
            // Log.d("asdasd", respuestaInfo.toString())
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<CalifFinal>()
                for(calificaciones in respuestaInfo){
                    if(calificaciones.contains("calif")){
                        val objCalif = Gson().fromJson("{"+calificaciones+"}", CalifFinal::class.java)
                        // Log.d("asdasd", objCalif.toString())
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
            //Log.d("asdasd", respuestaInfo.toString())

            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<Cardex>()
                var prom: String = "Null"
                for(cardex in respuestaInfo){
                    if(cardex.contains("Materia")){
                        val objCardex = Gson().fromJson("{$cardex}", Cardex::class.java)
                        //Log.d("asdasd", objCardex.toString())
                        arreglo.add(objCardex)
                    } else if(cardex.contains("PromedioGral")){
                        prom = Gson().fromJson("{$cardex}", CardexProm::class.java).toString()
                        //Log.d("Promediomamalon", prom)
                    }
                }
                Log.d("asdasd", prom+"/"+arreglo.toString())
                return prom+"/"+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }
}