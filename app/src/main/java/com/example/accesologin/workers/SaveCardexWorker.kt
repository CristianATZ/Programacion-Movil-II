package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Cardex_Entity
import com.example.accesologin.ui.screens.parseCardexList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

class SaveCardexWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando CARDEX", applicationContext)

        return try {
            val cardex = parseCardexList(inputData.getString("cardex").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosContainer.getUserCardexDao().deleteCardex()
                for (materia in cardex){
                    AlumnosContainer.getUserCardexDao().insertCardex(
                        Cardex_Entity(
                            id = 0,
                            ClvMat = materia.ClvMat,
                            ClvOfiMat = materia.ClvOfiMat,
                            Materia = materia.Materia,
                            Cdts = materia.Cdts,
                            Calif = materia.Calif,
                            Acred = materia.Acred,
                            S1 = materia.S1,
                            P1 = materia.P1,
                            A1 = materia.A1,
                            S2 = materia.S2,
                            P2 = materia.P2,
                            A2 = materia.A2,
                            S3 = materia.S3,
                            P3 = materia.P3,
                            A3 = materia.A3,
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