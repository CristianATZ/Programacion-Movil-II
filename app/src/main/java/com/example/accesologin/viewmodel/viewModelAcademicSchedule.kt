package com.example.accesologin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.accesologin.data.AlumnosRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.accesologin.AlumnosContainer
import kotlinx.coroutines.async

class viewModelAcademicSchedule(private val alumnosRepository: AlumnosRepository): ViewModel() {
    suspend fun getAcademicSchedule(): String {
        val TAG = "VIEWMODEL"
        Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCarga()
        }
        return schedule.await()
    }

    suspend fun getCalifByUnidad() : String {
        val TAG = "VIEWMODEL"
        Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCalificaciones()
        }
        return schedule.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                viewModelAcademicSchedule(alumnosRepository = alumnosAplication)
            }
        }
    }
}