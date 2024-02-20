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