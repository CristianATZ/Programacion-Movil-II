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
    @ColumnInfo(name = "contrasenia") val contrasenia: String = "",
    @ColumnInfo(name = "matricula") val matricula: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)

@Entity(tableName = "alumno")
data class Alumno_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "fechaReins") val fechaReins: String = "",
    @ColumnInfo(name = "semActual") val semActual: String = "",
    @ColumnInfo(name = "cdtosAcumulados") val cdtosAcumulados: String = "",
    @ColumnInfo(name = "cdtosActuales") val cdtosActuales: String = "",
    @ColumnInfo(name = "carrera") val carrera: String = "",
    @ColumnInfo(name = "matricula") val matricula: String = "",
    @ColumnInfo(name = "especialidad") val especialidad: String = "",
    @ColumnInfo(name = "modEducativo") val modEducativo: String = "",
    @ColumnInfo(name = "adeudo") val adeudo: String = "",
    @ColumnInfo(name = "urlFoto") val urlFoto: String = "",
    @ColumnInfo(name = "adeudoDescripcion") val adeudoDescription: String = "",
    @ColumnInfo(name = "inscrito") val inscrito: String = "",
    @ColumnInfo(name = "estatus") val estatus: String = "",
    @ColumnInfo(name = "lineamiento") val lineamiento: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)

@Entity(tableName = "carga")
data class Carga_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "Semipresencial") val Semipresencial: String = "",
    @ColumnInfo(name = "Observaciones") val Observaciones: String = "",
    @ColumnInfo(name = "Docente") val Docente: String = "",
    @ColumnInfo(name = "clvOficial") val clvOficial: String = "",
    @ColumnInfo(name = "Sabado") val Sabado: String = "",
    @ColumnInfo(name = "Viernes") val Viernes: String = "",
    @ColumnInfo(name = "Jueves") val Jueves: String = "",
    @ColumnInfo(name = "Miercoles") val Miercoles: String = "",
    @ColumnInfo(name = "Martes") val Martes: String = "",
    @ColumnInfo(name = "Lunes") val Lunes: String = "",
    @ColumnInfo(name = "EstadoMateria") val EstadoMateria: String = "",
    @ColumnInfo(name = "CreditosMateria") val CreditosMateria: String = "",
    @ColumnInfo(name = "Materia") val Materia: String = "",
    @ColumnInfo(name = "Grupo") val Grupo: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)


@Entity(tableName = "califUnidad")
data class CalifUnidad_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "Observaciones") val Observaciones: String = "",
    @ColumnInfo(name = "C13") val C13: String = "",
    @ColumnInfo(name = "C12") val C12: String = "",
    @ColumnInfo(name = "C11") val C11: String = "",
    @ColumnInfo(name = "C10") val C10: String = "",
    @ColumnInfo(name = "C9") val C9: String = "",
    @ColumnInfo(name = "C8") val C8: String = "",
    @ColumnInfo(name = "C7") val C7: String = "",
    @ColumnInfo(name = "C6") val C6: String = "",
    @ColumnInfo(name = "C5") val C5: String = "",
    @ColumnInfo(name = "C4") val C4: String = "",
    @ColumnInfo(name = "C3") val C3: String = "",
    @ColumnInfo(name = "C2") val C2: String = "",
    @ColumnInfo(name = "C1") val C1: String = "",
    @ColumnInfo(name = "UnidadesActivas") val UnidadesActivas: String = "",
    @ColumnInfo(name = "Materia") val Materia: String = "",
    @ColumnInfo(name = "Grupo") val Grupo: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)


@Entity(tableName = "califFinal")
data class CalifFinal_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "calif") val calif: String = "",
    @ColumnInfo(name = "acred") val acred: String = "",
    @ColumnInfo(name = "grupo") val grupo: String = "",
    @ColumnInfo(name = "materia") val materia: String = "",
    @ColumnInfo(name = "Observaciones") val Observaciones: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)

@Entity(tableName = "cardex")
data class Cardex_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "ClvMat") val ClvMat: String = "",
    @ColumnInfo(name = "ClvOfiMat") val ClvOfiMat: String = "",
    @ColumnInfo(name = "Materia") val Materia: String = "",
    @ColumnInfo(name = "Cdts") val Cdts: String = "",
    @ColumnInfo(name = "Calif") val Calif: String = "",
    @ColumnInfo(name = "Acred") val Acred: String = "",
    @ColumnInfo(name = "S1") val S1: String = "",
    @ColumnInfo(name = "P1") val P1: String = "",
    @ColumnInfo(name = "A1") val A1: String = "",
    @ColumnInfo(name = "S2") val S2: String = "",
    @ColumnInfo(name = "P2") val P2: String = "",
    @ColumnInfo(name = "A2") val A2: String = "",
    @ColumnInfo(name = "S3") val S3: String = "",
    @ColumnInfo(name = "P3") val P3: String = "",
    @ColumnInfo(name = "A3") val A3: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)

@Entity(tableName = "cardexProm")
data class CardexProm_Entity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(true) val id: Int = 0,
    @ColumnInfo(name = "PromedioGral") val PromedioGral: String = "",
    @ColumnInfo(name = "CdtsAcum") val CtdsAcum: String = "",
    @ColumnInfo(name = "CdtsPlan") val CdtsPlan: String = "",
    @ColumnInfo(name = "MatCursadas") val MatCursadas: String = "",
    @ColumnInfo(name = "MatAprobadas") val MatAprobadas: String = "",
    @ColumnInfo(name = "AvanceCtds") val AvanceCdts: String = "",
    @ColumnInfo(name = "fecha") val fecha: String = ""
)