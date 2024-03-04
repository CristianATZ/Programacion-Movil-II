package com.example.accesologin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.accesologin.data.AlumnosRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.accesologin.AlumnosContainer
import kotlinx.coroutines.async


class AlumnoViewModel(private val alumnosRepository: AlumnosRepository): ViewModel() {
    suspend fun getAcademicSchedule(): String {
        //val TAG = "VIEWMODEL"
        //Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCarga()
        }
        return schedule.await()
    }

    suspend fun getCalifByUnidad() : String {
        //val TAG = "VIEWMODEL"
        //Log.d(TAG, "ENTRANDO AL VIEWMODEL")
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
        //val TAG = "VIEWMODEL"
        //Log.d(TAG, "ENTRANDO AL VIEWMODEL")
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