package com.example.accesologin.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.accesologin.workers.PullCardexPromWorker
import com.example.accesologin.workers.PullCardexWorker
import com.example.accesologin.workers.PullCargaWorker
import com.example.accesologin.workers.PullFinalesWorker
import com.example.accesologin.workers.PullInfoWorker
import com.example.accesologin.workers.PullUnidadesWorker
import com.example.accesologin.workers.SaveCardexPromWorker
import com.example.accesologin.workers.SaveCargaWorker
import com.example.accesologin.workers.SaveFinalesWorker
import com.example.accesologin.workers.SaveInfoWorker
import com.example.accesologin.workers.SaveUnidadesWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

interface WorkerRepository {
    fun guardadoWorker()
    fun cargaWorker()
    fun cardexWorker()
    fun cardexPromWorker()
    fun unidadesWorker()
    fun finalesWorker()

    val outputWorkGuardado: Flow<WorkInfo>
    val outputWorkCarga: Flow<WorkInfo>
    val outputWorkCardex: Flow<WorkInfo>
    val outputWorkCardexProm: Flow<WorkInfo>
    val outputWorkUnidades: Flow<WorkInfo>
    val outputWorkFinales: Flow<WorkInfo>
}

class WorkerSupervisor(context: Context): WorkerRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun guardadoWorker() {
        var pullLogin = OneTimeWorkRequestBuilder<PullInfoWorker>()
            .addTag("PullInfoWorker")
            .build()

        var saveLogin = OneTimeWorkRequestBuilder<SaveInfoWorker>()
            .addTag("SaveInfoWorker")
            .build()

        workManager.beginUniqueWork(
            "PullSaveLogin",
            ExistingWorkPolicy.REPLACE,
            pullLogin
        ).then(saveLogin).enqueue()
    }

    override fun cargaWorker() {
        var pullCarga = OneTimeWorkRequestBuilder<PullCargaWorker>()
            .addTag("PullCargaWorker")
            .build()

        var saveCarga = OneTimeWorkRequestBuilder<SaveCargaWorker>()
            .addTag("SaveCargaWorker")
            .build()

        workManager.beginUniqueWork(
            "PullSaveCarga",
            ExistingWorkPolicy.REPLACE,
            pullCarga
        ).then(saveCarga).enqueue()
    }

    override fun cardexWorker() {
        var pullCardex = OneTimeWorkRequestBuilder<PullCardexWorker>()
            .addTag("PullCardexWorker")
            .build()

        var saveCardex = OneTimeWorkRequestBuilder<SaveCargaWorker>()
            .addTag("SaveCardexWorker")
            .build()

        workManager.beginUniqueWork(
            "PullSaveCardex",
            ExistingWorkPolicy.REPLACE,
            pullCardex
        ).then(saveCardex).enqueue()
    }

    override fun cardexPromWorker() {
        var pullCardexProm = OneTimeWorkRequestBuilder<PullCardexPromWorker>()
            .addTag("PullCardexPromWorker")
            .build()

        var saveCardexProm = OneTimeWorkRequestBuilder<SaveCardexPromWorker>()
            .addTag("SaveCardexPromWorker")
            .build()

        workManager.beginUniqueWork(
            "PullSaveCardexProm",
            ExistingWorkPolicy.REPLACE,
            pullCardexProm
        ).then(saveCardexProm).enqueue()
    }

    override fun unidadesWorker() {
        var pullUnidades = OneTimeWorkRequestBuilder<PullUnidadesWorker>()
            .addTag("PullUnidades")
            .build()

        var saveUnidades = OneTimeWorkRequestBuilder<SaveUnidadesWorker>()
            .addTag("SaveUnidades")
            .build()

        workManager.beginUniqueWork(
            "PullSaveUnidades",
            ExistingWorkPolicy.REPLACE,
            pullUnidades
        ).then(saveUnidades).enqueue()
    }

    override fun finalesWorker() {
        var pullFinales = OneTimeWorkRequestBuilder<PullFinalesWorker>()
            .addTag("PullFinales")
            .build()

        var saveFinales = OneTimeWorkRequestBuilder<SaveFinalesWorker>()
            .addTag("SaveFinales")
            .build()

        workManager.beginUniqueWork(
            "PullSaveFinales",
            ExistingWorkPolicy.REPLACE,
            pullFinales
        ).then(saveFinales).enqueue()
    }

    override val outputWorkGuardado: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("PullInfoWorker").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first()
            else null
        }

    override val outputWorkCarga: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("PullCargaWorker").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first()
            else null
        }


    override val outputWorkCardex: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("PullCardexWorker").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first()
            else null
        }

    override val outputWorkCardexProm: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("PullCardexPromWorker").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first()
            else null
        }

    override val outputWorkUnidades: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("PullUnidadesWorker").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first()
            else null
        }

    override val outputWorkFinales: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("PullFinalesWorker").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first()
            else null
        }
}
