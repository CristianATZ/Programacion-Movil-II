package com.example.accesologin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.accesologin.data.AlumnosRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.workers.PullCardexPromWorker
import com.example.accesologin.workers.PullCardexWorker
import com.example.accesologin.workers.PullCargaWorker
import com.example.accesologin.workers.PullFinalesWorker
import com.example.accesologin.workers.PullUnidadesWorker
import com.example.accesologin.workers.SaveCardexPromWorker
import com.example.accesologin.workers.SaveCardexWorker
import com.example.accesologin.workers.SaveCargaWorker
import com.example.accesologin.workers.SaveFinalesWorker
import com.example.accesologin.workers.SaveUnidadesWorker
import kotlinx.coroutines.async


class AlumnoViewModel(private val alumnosRepository: AlumnosRepository): ViewModel() {
    private val workManager = WorkManager.getInstance()
    private val db = AlumnosContainer.getDataBase()

    // OBTENCION DE INFO DEL SICE ------------------------------------
    suspend fun getAcademicSchedule(): String {
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCarga()
        }
        return schedule.await()
    }

    suspend fun getCalifByUnidad() : String {
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCalificaciones()
        }
        return schedule.await()
    }

    suspend fun getCalifFinal(): String {
        val grades = viewModelScope.async {
            alumnosRepository.obtenerCalifFinales()
        }
        return grades.await()
    }

    suspend fun getCardexByAlumno(): String {
        val cardex = viewModelScope.async{
            alumnosRepository.obtenerCardex()
        }
        return cardex.await()
    }

    // OBTENCION DE INFO DE LA BDD
    suspend fun getAcademicScheduleDB(): String {
        return try {
            Log.d("AlumnoViewModel", db.UserCargaDao().getCarga().toString())
            db.UserCargaDao().getCarga().toString()
        } catch (e: Exception){
            ""
        }
    }


    // WORKERS ---------------------------------------------------------------
    internal fun cargaWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullCargaWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<SaveCargaWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun cardexWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullCardexWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<SaveCardexWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun cardexPromWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullCardexPromWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<SaveCardexPromWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun unidadesWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullUnidadesWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<SaveUnidadesWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun finalesWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullFinalesWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<SaveFinalesWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                AlumnoViewModel(alumnosRepository = alumnosAplication)
            }
        }
    }

}