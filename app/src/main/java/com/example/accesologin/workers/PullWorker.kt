package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Acceso_Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class PullWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Trayendo datos de SICE", applicationContext)

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