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
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.data.OfflineRepository
import com.example.accesologin.workers.PullInfoWorker
import com.example.accesologin.workers.SaveInfoWorker

class LoginViewModel(
    private val alumnosRepository: AlumnosRepository,
    private val offlineRepository: OfflineRepository
): ViewModel() {
    var matricula by mutableStateOf("")
    var password by mutableStateOf("")

    // Actualizar matricula
    fun updateMatricula(value: String) {
        matricula = value
    }

    // Actulziar password
    fun updatePassword(value: String) {
        password = value
    }

    private val workManager = WorkManager.getInstance()
    internal fun guardadoWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullInfoWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<SaveInfoWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    // obtener acceso a sice
    suspend fun getAccess(matricula: String, password: String): Boolean {
        //val TAG = "VIEWMODEL"
        //Log.d(TAG, matricula+", "+password+" = "+alumnosRepository.obtenerAcceso(matricula, password).toString())
        return alumnosRepository.obtenerAcceso(matricula, password)
    }

    // obtener info de sice
    suspend fun getInfo(): String {
        val info = viewModelScope.async {
            alumnosRepository.obtenerInfo()
        }
        return info.await()
    }


    // METODOS DEL REPOSITORIO OFFLINE
    suspend fun getAccessDB(matricula: String, password: String): Boolean{
        Log.d("LoginViewModel", offlineRepository.getAccesDB(matricula, password).toString())
        return offlineRepository.getAccesDB(matricula, password).acceso.equals(true)
    }

    

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                val alumnosAplicationDB = application.container.offlineRepository
                LoginViewModel(
                    alumnosRepository = alumnosAplication,
                    offlineRepository = alumnosAplicationDB
                )
            }
        }
    }
}