package com.example.accesologin.workers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Alumno_Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SaveInfoWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando INFO", applicationContext)

        return try {
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosContainer.getUserInfoDao().deleteAlumnos()
                AlumnosContainer.getUserInfoDao().insertAlumno(
                    Alumno_Entity(
                        id = 0,
                        nombre = inputData.getString("nombre").toString(),
                        fechaReins = inputData.getString("fechaReins").toString(),
                        semActual = inputData.getString("semActual").toString(),
                        cdtosAcumulados = inputData.getString("cdtosAcumulados").toString(),
                        cdtosActuales = inputData.getString("cdtosActuales").toString(),
                        carrera = inputData.getString("carrera").toString(),
                        matricula = inputData.getString("matricula").toString(),
                        especialidad = inputData.getString("especialidad").toString(),
                        modEducativo = inputData.getString(" modEducativo").toString(),
                        adeudo = inputData.getString("adeudo").toString(),
                        urlFoto = inputData.getString("urlFoto").toString(),
                        inscrito = inputData.getString("inscrito").toString(),
                        estatus = inputData.getString( "estatus").toString(),
                        lineamiento = inputData.getString("lineamiento").toString(),
                        fecha = inputData.getString("fecha").toString()
                    )
                )
            }
            Result.success()
        } catch(exception: Exception){
            Result.failure()
        }
    }
}