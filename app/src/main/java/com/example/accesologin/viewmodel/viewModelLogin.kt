package com.example.accesologin.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.accesologin.data.AlumnosRepository
import kotlinx.coroutines.async
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.accesologin.AlumnosContainer

class viewModelLogin(private val alumnosRepository: AlumnosRepository): ViewModel() {
    var matricula by mutableStateOf("")
    var password by mutableStateOf("")

    // Actualizar matricula
    fun updateMatricula(value: String){
        matricula = value
    }

    // Actulziar password
    fun updatePassword(value: String){
        password = value
    }

    // obtener acceso a sice
    suspend fun getAccess(matricula: String, password: String): Boolean {
        val TAG = "VIEWMODEL"
        Log.d(TAG, matricula+", "+password+" = "+alumnosRepository.obtenerAcceso(matricula, password).toString())
        return alumnosRepository.obtenerAcceso(matricula, password)
    }

    // obtener info de sice
    suspend fun getInfo(): String {
        val info = viewModelScope.async {
            alumnosRepository.obtenerInfo()
        }
        return info.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                viewModelLogin(alumnosRepository = alumnosAplication)
            }
        }
    }
}