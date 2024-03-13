package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Carga_Entity
import com.example.accesologin.ui.screens.parseCargaList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

class SaveCargaWorker(context: Context, workerParams: WorkerParameters):  Worker(context, workerParams){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando CARGA", applicationContext)

        return try {
            val carga = parseCargaList(inputData.getString("carga").toString())
            Log.d("SaveCargaWorker", carga.toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosContainer.getUserCargaDao().deleteCargas()
                for (materia in carga){
                    AlumnosContainer.getUserCargaDao().insertCarga(
                        Carga_Entity(
                            id = 0,
                            Semipresencial = materia.Semipresencial,
                            Observaciones = materia.Observaciones,
                            Docente = materia.Docente,
                            clvOficial = materia.clvOficial,
                            Sabado = materia.Sabado,
                            Viernes = materia.Viernes,
                            Jueves = materia.Jueves,
                            Miercoles = materia.Miercoles,
                            Martes = materia.Martes,
                            Lunes = materia.Lunes,
                            EstadoMateria = materia.EstadoMateria,
                            CreditosMateria = materia.CreditosMateria,
                            Materia = materia.Materia,
                            Grupo = materia.Grupo,
                            fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                        )
                    )
                }
            }
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}