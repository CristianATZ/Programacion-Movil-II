package com.example.accesologin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno_Entity
import com.example.accesologin.model.CalifFinal_Entity
import com.example.accesologin.model.CalifUnidad_Entity
import com.example.accesologin.model.CardexProm_Entity
import com.example.accesologin.model.Cardex_Entity
import com.example.accesologin.model.Carga_Entity

@Database(entities = [Acceso_Entity::class, Alumno_Entity::class, Carga_Entity::class, CalifUnidad_Entity::class, CalifFinal_Entity::class, Cardex_Entity::class, CardexProm_Entity::class], version = 1)
abstract class SICEDatabase: RoomDatabase() {
    abstract fun UserLoginDao():  AccesoDao
    abstract fun UserInfoDao(): AlumnoDao
    abstract fun UserCargaDao(): CargaDao
    abstract fun UserCalifUnidadDao(): CalifUnidadDao
    abstract fun UserCalifFinalDao(): CalifFinalDao
    abstract fun UserCardexDao(): CardexDao
    abstract fun UserCardexPromDao(): CardexPromDao

    companion object {
        @Volatile
        private var Instance: SICEDatabase ?= null

        fun getDatabase(context: Context): SICEDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, SICEDatabase::class.java, "sicenet_database")
                    .allowMainThreadQueries()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}