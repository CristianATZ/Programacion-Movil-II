package com.example.accesologin.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.ui.screens.parseCardexProm

class PullCardexPromWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var alumnosRepository = (ctx.applicationContext as AlumnosContainer).container.alumnosRepository

    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo DATOS ADICIONALES del Cardex", applicationContext)
        sleep()

        return try {
            val cardexProm = parseCardexProm(alumnosRepository.obtenerCardex())
            var outputData = workDataOf("cardexProm" to cardexProm.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}