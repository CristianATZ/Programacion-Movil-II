package com.example.accesologin.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.ui.screens.parseCargaList

class PullCargaWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    var alumnosRepository = (ctx.applicationContext as AlumnosContainer).container.alumnosRepository

    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo CARGA de SICE", applicationContext)
        sleep()

        return try {
            val carga = parseCargaList(alumnosRepository.obtenerCarga())
            Log.d("HOLA DESDE EL WORKER", carga.toString())
            var outputData = workDataOf(
                "carga" to carga.toString()
            )
            Log.d("SALUDAZOS HASTA CULIACAN", outputData.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}