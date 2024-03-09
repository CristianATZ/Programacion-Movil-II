package com.example.accesologin.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.accesologin.R

@SuppressLint("MissingPermission")
fun makeStatusNotification(message: String, context: Context){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val name = "SICENet"
        val descripton = "Notificacion"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("SICENet Notificacion", name, importance)
        channel.description = descripton

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, "SICENet Notificacion")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("SICENet")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    NotificationManagerCompat.from(context).notify(1, builder.build())
}