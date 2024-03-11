package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.CalifUnidad_Entity
import com.example.accesologin.ui.screens.parseUnidadList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SaveUnidadesWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando UNIDADES", applicationContext)

        return try {
            val califs = parseUnidadList(inputData.getString("califs").toString())
            CoroutineScope(Dispatchers.IO).launch {
                for (materia in califs){
                    AlumnosContainer.getUserCalifUnidadDao().insertCalifUnidad(
                        CalifUnidad_Entity(
                            id = 0,
                            Observaciones = materia.Observaciones,
                            C13 = materia.C13,
                            C12 = materia.C12,
                            C11 = materia.C11,
                            C10 = materia.C10,
                            C9 = materia.C9,
                            C8 = materia.C8,
                            C7 = materia.C7,
                            C6 = materia.C6,
                            C5 = materia.C5,
                            C4 = materia.C4,
                            C3 = materia.C3,
                            C2 = materia.C2,
                            C1 = materia.C1,
                            UnidadesActivas = materia.UnidadesActivas,
                            Materia = materia.Materia,
                            Grupo = materia.Grupo,
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