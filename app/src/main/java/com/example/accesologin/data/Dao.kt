package com.example.accesologin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno_Entity
import com.example.accesologin.model.CalifFinal_Entity
import com.example.accesologin.model.CalifUnidad_Entity
import com.example.accesologin.model.CardexProm_Entity
import com.example.accesologin.model.Cardex_Entity
import com.example.accesologin.model.Carga_Entity

@Dao
interface AccesoDao {
    @Insert
    fun insertAcceso(item: Acceso_Entity)

    @Query("SELECT * FROM acceso WHERE matricula=:_matricula AND contrasenia=:_contrasenia")
    fun getAccess(_matricula: String, _contrasenia: String): Acceso_Entity?
}

@Dao
interface AlumnoDao {
    @Insert
    fun insertAlumno(item: Alumno_Entity)

    @Query("SELECT * FROM alumno")
    fun getAlumno(): Alumno_Entity
}

@Dao
interface CargaDao {
    @Insert
    fun insertCarga(item: Carga_Entity)
}

@Dao
interface CalifUnidadDao {
    @Insert
    fun insertCalifUnidad(item: CalifUnidad_Entity)
}

@Dao
interface CalifFinalDao {
    @Insert
    fun insertCalifFinal(item: CalifFinal_Entity)
}

@Dao
interface CardexDao {
    @Insert
    fun insertCardex(item: Cardex_Entity)
}

@Dao
interface CardexPromDao {
    @Insert
    fun insertCardexProm(item: CardexProm_Entity)
}