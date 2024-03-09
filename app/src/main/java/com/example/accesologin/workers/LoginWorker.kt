package com.example.accesologin.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class LoginWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}