package com.example.accesologin.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.accesologin.data.AlumnosRepository
import kotlinx.coroutines.async
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.room.Room
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.data.OfflineRepository
import com.example.accesologin.data.WorkerRepository
import com.example.accesologin.workers.PullInfoWorker
import com.example.accesologin.workers.SaveInfoWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val alumnosRepository: AlumnosRepository,
    private val offlineRepository: OfflineRepository,
    private val workerRepository: WorkerRepository
): ViewModel() {
    // Variables para la matricula, password y el acceso a la BDD de Room
    var matricula by mutableStateOf("S20120202")
    var password by mutableStateOf("7Sf_/r6Q")
    private val db = AlumnosContainer.getDataBase()

    fun updateMatricula(value: String) {
        matricula = value
    }

    fun updatePassword(value: String) {
        password = value
    }


    // Métodos para tomar los datos de las peticiones realizadas al servidor --------------------------------------
    suspend fun getAccess(matricula: String, password: String): Boolean {
        return alumnosRepository.obtenerAcceso(matricula, password)
    }

    suspend fun getInfo(): String {
        val info = viewModelScope.async {
            alumnosRepository.obtenerInfo()
        }
        return info.await()
    }


    // Métodos para obtener los datos de las tablas de la BDD de Room -------------------------------
    suspend fun getAccessDB(matricula: String, password: String): Boolean{
        return try {
            db.UserLoginDao().getAccess(matricula, password).acceso == "true"
        } catch (exception: Exception){
            false
        }
    }

    suspend fun getInfoDB(): String {
        return try {
            db.UserInfoDao().getAlumno().toString()
        } catch (e: Exception){
            ""
        }
    }


    // Función que invoca al worker de guardado de info del alumno -----------------------------------
    fun guardadoWorker(){
        return workerRepository.guardadoWorker()
    }


    // Creación de un objeto asociado a la clase
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                val alumnosAplicationDB = application.container.offlineRepository
                val workerAplication = application.container.workerRepository
                LoginViewModel(
                    alumnosRepository = alumnosAplication,
                    offlineRepository = alumnosAplicationDB,
                    workerRepository = workerAplication
                )
            }
        }
    }
}

