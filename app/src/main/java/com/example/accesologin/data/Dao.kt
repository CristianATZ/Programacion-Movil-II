package com.example.accesologin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno_Entity

@Dao
interface AccesoDao {
    @Insert
    fun insertAcceso(item: Acceso_Entity)
}


@Dao
interface AlumnoDao {
    @Insert
    fun insertAlumno(item: Alumno_Entity)
}
