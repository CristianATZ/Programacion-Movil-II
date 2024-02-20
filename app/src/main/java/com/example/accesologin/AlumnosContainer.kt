package com.example.accesologin

import android.app.Application
import com.example.accesologin.data.AppContainer
import com.example.accesologin.data.DefaultAppContainer

class AlumnosContainer: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}