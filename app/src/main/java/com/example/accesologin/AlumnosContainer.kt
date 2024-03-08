package com.example.accesologin

import android.app.Application
import androidx.room.Room
import com.example.accesologin.data.AccesoDao
import com.example.accesologin.data.AppContainer
import com.example.accesologin.data.DefaultAppContainer
import com.example.accesologin.data.SICEDatabase

class AlumnosContainer: Application() {
    lateinit var container: AppContainer
    private var db: SICEDatabase ?= null

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

    fun getAppContainer(): AppContainer {
        return container
    }

    private fun getDB(): SICEDatabase {
        return if(db!=null){
            db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                SICEDatabase::class.java, "SICEDatabase"
            ).fallbackToDestructiveMigration()
                .build()
            db!!
        }
    }

    companion object {
        private var instance: AlumnosContainer?=null

        fun getUserLoginDao(): AccesoDao {
            return instance!!.getDB().UserLoginDao()
        }
    }
}