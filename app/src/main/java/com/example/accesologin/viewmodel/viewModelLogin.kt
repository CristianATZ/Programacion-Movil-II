package com.example.accesologin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.accesologin.data.Repository
import kotlinx.coroutines.async
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.accesologin.AlumnosContainer

class viewModelLogin(private val Repository: Repository): ViewModel() {
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
        return Repository.getAccess(matricula, password)
    }

    // obtener info de sice
    suspend fun getInfo(): String {
        val info = viewModelScope.async {
            Repository.getInfo()
        }
        return info.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosApplication = application.container.repository
                viewModelLogin(Repository = alumnosApplication)
            }
        }
    }
}