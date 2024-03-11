package com.example.accesologin

import android.app.Application
import androidx.room.Room
import com.example.accesologin.data.AccesoDao
import com.example.accesologin.data.AlumnoDao
import com.example.accesologin.data.AppContainer
import com.example.accesologin.data.CalifFinalDao
import com.example.accesologin.data.CalifUnidadDao
import com.example.accesologin.data.CardexDao
import com.example.accesologin.data.CardexPromDao
import com.example.accesologin.data.CargaDao
import com.example.accesologin.data.DefaultAppContainer
import com.example.accesologin.data.SICEDatabase

class AlumnosContainer: Application() {
    lateinit var container: AppContainer
    private var db: SICEDatabase ?= null

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

    init {
        instance = this
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

        fun getUserInfoDao(): AlumnoDao {
            return instance!!.getDB().UserInfoDao()
        }

        fun getUserCargaDao(): CargaDao {
            return instance!!.getDB().UserCargaDao()
        }

        fun getUserCalifUnidadDao(): CalifUnidadDao {
            return instance!!.getDB().UserCalifUnidadDao()
        }

        fun getUserCalifFinalDao(): CalifFinalDao {
            return instance!!.getDB().UserCalifFinalDao()
        }

        fun getUserCardexDao(): CardexDao {
            return instance!!.getDB().UserCardexDao()
        }

        fun getUserCardexPromDao(): CardexPromDao {
            return instance!!.getDB().UserCardexPromDao()
        }
    }
}