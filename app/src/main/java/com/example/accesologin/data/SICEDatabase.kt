package com.example.accesologin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno_Entity

@Database(entities = [Acceso_Entity::class, Alumno_Entity::class], version = 1)
abstract class SICEDatabase: RoomDatabase() {
    abstract fun UserLoginDao():  AccesoDao
    abstract fun UserInfoDao(): AlumnoDao
}