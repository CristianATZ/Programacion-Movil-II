package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.CardexProm_Entity
import com.example.accesologin.ui.screens.parseCardexProm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SaveCardexPromWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando DATOS ADICIONALES", applicationContext)

        return try {
            val cardexProm = parseCardexProm(inputData.getString("cardexProm").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosContainer.getUserCardexDao().deleteCardex()
                AlumnosContainer.getUserCardexPromDao().insertCardexProm(
                    CardexProm_Entity(
                        id = 0,
                        PromedioGral = cardexProm!!.PromedioGral,
                        CdtsPlan = cardexProm!!.CdtsPlan,
                        CtdsAcum = cardexProm!!.CdtsAcum,
                        MatCursadas = cardexProm!!.MatCursadas,
                        MatAprobadas = cardexProm!!.MatAprobadas,
                        AvanceCdts = cardexProm!!.AvanceCdts,
                        fecha = LocalDateTime.now().toString()
                    )
                )
            }
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}