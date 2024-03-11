package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.CalifFinal_Entity
import com.example.accesologin.ui.screens.parseCalifList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SaveFinalesWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando Calificaciones Finales", applicationContext)

        return try {
            val califs = parseCalifList(inputData.getString("califs").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosContainer.getUserCalifFinalDao().deleteFinales()
                for (materia in califs){
                    AlumnosContainer.getUserCalifFinalDao().insertCalifFinal(
                        CalifFinal_Entity(
                            id = 0,
                            calif = materia.calif,
                            acred = materia.acred,
                            grupo = materia.grupo,
                            materia = materia.materia,
                            Observaciones = materia.Observaciones,
                            fecha = LocalDateTime.now().toString()
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