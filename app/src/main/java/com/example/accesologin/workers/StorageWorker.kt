package com.example.accesologin.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class StorageWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        makeStatusNotification("Almacenando datos", applicationContext)

        return try {
            /*
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosContainer.getUserLoginDao().insertAcceso(
                    Acceso_Entity(
                        id = 0,
                        acceso = result.acceso,
                        estatus = result.estatus,
                        password = password,
                        matricula = matricula,
                        fecha = LocalDateTime.now().toString()
                    )
                )
            }
             */
            Result.success()
        } catch(exception: Exception){
            Result.failure()
        }
    }
}