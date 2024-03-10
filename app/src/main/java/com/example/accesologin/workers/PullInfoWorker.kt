package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.ui.screens.parseInfoAlumno
import java.time.LocalDateTime


class PullInfoWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    var alumnosRepository = (ctx.applicationContext as AlumnosContainer).container.alumnosRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo INFO de SICE", applicationContext)
        sleep()

        return try {
            val alumno = parseInfoAlumno(alumnosRepository.obtenerInfo())
            //Log.d("HOLA DESDE EL WORKER", alumno.toString())
            var outputData = workDataOf(
                "nombre" to alumno.nombre,
                "fechaReins" to alumno.fechaReins,
                "semActual" to alumno.semActual,
                "cdtosAcumulados" to alumno.cdtosAcumulados,
                "cdtosActuales" to alumno.cdtosActuales,
                "carrera" to alumno.carrera,
                "matricula" to alumno.matricula,
                "especialidad" to alumno.especialidad,
                "modEducativo" to alumno.modEducativo,
                "adeudo" to alumno.adeudo,
                "urlFoto" to alumno.urlFoto,
                "adeudoDescripcion" to alumno.adeudoDescripcion,
                "inscrito" to alumno.inscrito,
                "estatus" to alumno.estatus,
                "lineamiento" to alumno.lineamiento,
                "fecha" to LocalDateTime.now().toString()
            )
            //Log.d("SALUDAZOS HASTA CULIACAN", outputData.toString())
            Result.success(outputData)
        } catch(exception: Exception){
            Result.failure()
        }
    }
}
