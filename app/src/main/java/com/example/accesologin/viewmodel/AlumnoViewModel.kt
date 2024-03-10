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
import com.example.accesologin.workers.PullWorker
import com.example.accesologin.workers.StorageWorker
import kotlinx.coroutines.async


class AlumnoViewModel(private val alumnosRepository: AlumnosRepository): ViewModel() {
    private val workManager = WorkManager.getInstance()

    internal fun guardadoWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<StorageWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

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