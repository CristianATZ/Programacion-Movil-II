package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.ui.screens.parseCardexList

class PullCardexWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    var alumnosRepository = (ctx.applicationContext as AlumnosContainer).container.alumnosRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo CARDEX de SICE", applicationContext)
        sleep()

        return try {
            val obj = alumnosRepository.obtenerCardex().split("~")
            val cardex = obj[1]
            //Log.d("PullCardexWorker", cardex.toString())
            var outputData = workDataOf("cardex" to cardex)
            Log.d("PullCardexWorker", outputData.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}