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
    var semipresencial: String,
    var observaciones: String,
    var docente: String,
    var clvOficial: String,
    var sabado: String,
    var viernes: String,
    var jueves: String,
    var miercoles: String,
    var martes: String,
    var lunes: String,
    var estadoMateria: String,
    var creditosMateria: String,
    var materia: String,
    var grupo: String
)

data class ListaCarga(
    var carga: List<Carga>
)