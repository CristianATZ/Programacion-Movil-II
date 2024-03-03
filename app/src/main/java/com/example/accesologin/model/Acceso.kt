package com.example.accesologin.model

data class Acceso (
    var acceso: String,
    var estatus: String,
    var contrasenia: String,
    var matricula: String
)

data class Alumno (
    var nombre:String="",
    var fechaReins:String="",
    var semActual:String="",
    var cdtosAcumulados:Int=0,
    var cdtosActuales:Int=0,
    var carrera:String="",
    var matricula:String="",
    var especialidad:String="",
    var modEducativo:String="",
    var adeudo:String="",
    var urlFoto:String="",
    var adeudoDescripcion:String="",
    var inscrito:Boolean=false,
    var estatus:String="",
    var lineamiento:Int=0,
)

data class Carga(
    var Semipresencial: String,
    var Observaciones: String,
    var Docente: String,
    var clvOficial: String,
    var Sabado: String,
    var Viernes: String,
    var Jueves: String,
    var Miercoles: String,
    var Martes: String,
    var Lunes: String,
    var EstadoMateria: String,
    var CreditosMateria: String,
    var Materia: String,
    var Grupo: String
)

data class CalificacionByUnidad(
    var Observaciones: String,
    var C13: String,
    var C12: String,
    var C11: String,
    var C10: String,
    var C9: String,
    var C8: String,
    var C7: String,
    var C6: String,
    var C5: String,
    var C4: String,
    var C3: String,
    var C2: String,
    var C1: String,
    var UnidadesActivas : String,
    var Materia : String,
    var Grupo : String
) {
    fun concatenarC(hastaNumero: Int): String {
        val variablesC = (1..hastaNumero).map { "C$it" }
        return variablesC.joinToString(separator = ",") { getVariableCValue(it) }
    }

    private fun getVariableCValue(variableC: String): String {
        return when (variableC) {
            "C13" -> C13
            "C12" -> C12
            "C11" -> C11
            "C10" -> C10
            "C9" -> C9
            "C8" -> C8
            "C7" -> C7
            "C6" -> C6
            "C5" -> C5
            "C4" -> C4
            "C3" -> C3
            "C2" -> C2
            "C1" -> C1
            else -> ""
        }
    }
}