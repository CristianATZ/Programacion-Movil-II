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
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.data.OfflineRepository
import com.example.accesologin.data.WorkerRepository
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class AlumnoViewModel(
    private val alumnosRepository: AlumnosRepository,
    private val offlineRepository: OfflineRepository,
    private val workerRepository: WorkerRepository
): ViewModel() {
    private val db = AlumnosContainer.getDataBase()

    // Variable que monitorea el estado del worker de la información del alumno
    val workerUiStateInfo: StateFlow<WorkerInfoState> = workerRepository.outputWorkGuardado
        .map { info ->
            when {
                info.state.isFinished -> {
                    WorkerInfoState.Complete
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerInfoState.Default
                }
                else -> WorkerInfoState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WorkerInfoState.Default
        )


    val workerUiStateCarga: StateFlow<WorkerCargaState> = workerRepository.outputWorkCarga
        .map { info ->
            when {
                info.state.isFinished -> {
                    WorkerCargaState.Complete
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerCargaState.Default
                }
                else -> WorkerCargaState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WorkerCargaState.Default
        )

    val workerUiStateCardex: StateFlow<WorkerCardexState> = workerRepository.outputWorkCardex
        .map { info ->
            when {
                info.state.isFinished -> {
                    WorkerCardexState.Complete
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerCardexState.Default
                }
                else -> WorkerCardexState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WorkerCardexState.Default
        )

    val workerUiStateFinals: StateFlow<WorkerFinalsState> = workerRepository.outputWorkFinales
        .map { info ->
            when {
                info.state.isFinished -> {
                    WorkerFinalsState.Complete
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerFinalsState.Default
                }
                else -> WorkerFinalsState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WorkerFinalsState.Default
        )

    val workerUiStateUnits: StateFlow<WorkerUnitsState> = workerRepository.outputWorkUnidades
        .map { info ->
            when {
                info.state.isFinished -> {
                    WorkerUnitsState.Complete
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerUnitsState.Default
                }
                else -> WorkerUnitsState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = WorkerUnitsState.Default
        )


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
            // Log.d("y es la marca RE-GIS-TRADA", db.UserCargaDao().getCarga().toString())
            db.UserCargaDao().getCarga().toString()
            //offlineRepository.getCargaDB().toString()
        } catch (e: Exception){
            ""
        }
    }


    suspend fun getCardexByAlumnoDB(): String {
        return try {
            //Log.d("AlumnoViewModel", db.UserCardexDao().getCardex().toString())
            db.UserCardexDao().getCardex().toString()
            //offlineRepository.getCardexDB().toString()
        } catch (e: Exception){
            ""
        }
    }

    suspend fun getCalifByUnidadDB(): String {
        return try {
            db.UserCalifUnidadDao().getCalifsUnidad().toString()
            //offlineRepository.getCalifUnidadDB().toString()
        } catch (e: Exception){
            ""
        }
    }

    suspend fun getCalifFinalDB(): String {
        return try {
            db.UserCalifFinalDao().getCalifsFinal().toString()
            //offlineRepository.getCalifFinalDB().toString()
        } catch (e: Exception){
            ""
        }
    }

    // WORKERS
    fun cargaWorker(){
        return workerRepository.cargaWorker()
    }

    fun unidadesWorker(){
        return workerRepository.unidadesWorker()
    }

    fun finalesWorker(){
        return workerRepository.finalesWorker()
    }

    fun cardexWorker(){
        return workerRepository.cardexWorker()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosContainer)
                val alumnosAplication = application.container.alumnosRepository
                val workerAplication = application.container.workerRepository
                val offlineAplication = application.container.offlineRepository
                AlumnoViewModel(
                    alumnosRepository = alumnosAplication,
                    workerRepository = workerAplication,
                    offlineRepository = offlineAplication
                )
            }
        }
    }

}


sealed interface WorkerInfoState {
    object Default: WorkerInfoState
    object Loading: WorkerInfoState
    object Complete: WorkerInfoState
}

sealed interface WorkerCargaState {
    object Default: WorkerCargaState
    object Loading: WorkerCargaState
    object Complete: WorkerCargaState
}

sealed interface WorkerCardexState {
    object Default: WorkerCardexState
    object Loading: WorkerCardexState
    object Complete: WorkerCardexState
}

sealed interface WorkerFinalsState {
    object Default: WorkerFinalsState
    object Loading: WorkerFinalsState
    object Complete: WorkerFinalsState
}

sealed interface WorkerUnitsState {
    object Default: WorkerUnitsState
    object Loading: WorkerUnitsState
    object Complete: WorkerUnitsState
}