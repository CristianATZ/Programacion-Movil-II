package com.example.accesologin.data

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM acceso WHERE matricula=:mat AND contrasenia=:pass")
   suspend fun getAccess(mat: String, pass: String): Acceso_Entity

    @Query("DELETE FROM acceso")
    suspend fun deleteAccesos()
}

@Dao
interface AlumnoDao {
    @Insert
    fun insertAlumno(item: Alumno_Entity)

    @Query("SELECT * FROM alumno")
    suspend fun getAlumno(): Alumno_Entity

    @Query("DELETE FROM alumno")
    suspend fun deleteAlumnos()
}

@Dao
interface CargaDao {
    @Insert
    fun insertCarga(item: Carga_Entity)

    @Query("SELECT * FROM carga")
    suspend fun getCarga(): List<Carga_Entity>

    @Query("DELETE FROM carga")
    suspend fun deleteCargas()
}

@Dao
interface CalifUnidadDao {
    @Insert
    fun insertCalifUnidad(item: CalifUnidad_Entity)

    @Query("SELECT * FROM califUnidad")
    suspend fun getCalifsUnidad(): List<CalifUnidad_Entity>

    @Query("DELETE FROM califUnidad")
    suspend fun deleteUnidades()
}

@Dao
interface CalifFinalDao {
    @Insert
    fun insertCalifFinal(item: CalifFinal_Entity)

    @Query("SELECT * FROM califFinal")
    suspend fun getCalifsFinal(): List<CalifFinal_Entity>

    @Query("DELETE FROM califFinal")
    suspend fun deleteFinales()
}

@Dao
interface CardexDao {
    @Insert
    fun insertCardex(item: Cardex_Entity)

    @Query("SELECT * FROM cardex")
    suspend fun getCardex(): List<Cardex_Entity>

    @Query("DELETE FROM cardex")
    suspend fun deleteCardex()
}

@Dao
interface CardexPromDao {
    @Insert
    fun insertCardexProm(item: CardexProm_Entity)

    @Query("SELECT * FROM cardexProm")
    suspend fun getCardexProm(): CardexProm_Entity

    @Query("DELETE FROM cardexProm")
    suspend fun deleteCardexProm()
}