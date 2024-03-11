package com.example.accesologin.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.ui.screens.parseUnidadList

class PullUnidadesWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params){
    val alumnosRepository = (ctx.applicationContext as AlumnosContainer).container.alumnosRepository
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo UNIDADES de SICE", applicationContext)
        sleep()

        return try {
            val califs = parseUnidadList(alumnosRepository.obtenerCalificaciones())
            var outputData = workDataOf("califs" to califs.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}