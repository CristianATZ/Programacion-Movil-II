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
import androidx.work.WorkManager
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.data.OfflineRepository
import com.example.accesologin.workers.PullInfoWorker
import com.example.accesologin.workers.SaveInfoWorker
import kotlinx.coroutines.launch

class LoginViewModel(
    private val alumnosRepository: AlumnosRepository,
    private val offlineRepository: OfflineRepository
): ViewModel() {
    var matricula by mutableStateOf("S20120202")
    var password by mutableStateOf("7Sf_/r6Q")
    private val db = AlumnosContainer.getDataBase()

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