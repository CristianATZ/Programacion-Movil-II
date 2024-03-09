package com.example.accesologin.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acceso")
data class Acceso_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "acceso") val acceso: String = "",
    @ColumnInfo(name = "estatus") val estatus: String = "",
    @ColumnInfo(name = "contrasenia") val password: String = "",
    @ColumnInfo(name = "matricula") val matricula: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)

@Entity(tableName = "alumno")
data class Alumno_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "fechaReins") val fechaReins: String = "",
    @ColumnInfo(name = "semActual") val semActual: String = "",
    @ColumnInfo(name = "cdtosAcumulados") val cdtosAcumulados: Int = 0,
    @ColumnInfo(name = "cdtosActuales") val cdtosActuales: Int = 0,
    @ColumnInfo(name = "carrera") val carrera: String = "",
    @ColumnInfo(name = "matricula") val matricula: String = "",
    @ColumnInfo(name = "especialidad") val especialidad: String = "",
    @ColumnInfo(name = "modEducativo") val modEducativo: String = "",
    @ColumnInfo(name = "adeudo") val adeudo: String = "",
    @ColumnInfo(name = "urlFoto") val urlFoto: String = "",
    @ColumnInfo(name = "adeudoDescripcion") val adeudoDescription: String = "",
    @ColumnInfo(name = "inscrito") val inscrito: Boolean = false,
    @ColumnInfo(name = "estatus") val estatus: String = "",
    @ColumnInfo(name = "lineamiento") val lineamiento: Int = 0,
    @ColumnInfo(name = "fecha") val fecha: String = ""
)


