package com.example.accesologin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.model.Carga
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.viewModelLogin
import kotlinx.coroutines.launch

@Composable
fun AcademicScheduleScreen(
    navController: NavController,
    text: String?
){
    val carga = parseCargaList(text.toString())

    LazyColumn{
        item{
            for(materia in carga){
                Column {
                    Text(materia.Materia)
                    Text(materia.Docente)
                    Text("Grupo: " + materia.Grupo)
                    Text("Lunes: " + materia.Lunes)
                    Text("Martes: " + materia.Martes)
                    Text("Miercoles: " + materia.Miercoles)
                    Text("Jueves: " + materia.Jueves)
                    Text("Viernes: " + materia.Viernes)
                    Text("")
                }
            }
        }
    }
    
}


fun parseCargaList(input: String): List<Carga> {
    val cargaRegex = Regex("Carga\\((.*?)\\)")

    return cargaRegex
        .findAll(input)
        .map { matchResult ->
            val cargaParams = matchResult.groupValues[1]
            val cargaMap = cargaParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            Carga(
                cargaMap["Semipresencial"] ?: "",
                cargaMap["Observaciones"] ?: "",
                cargaMap["Docente"] ?: "",
                cargaMap["clvOficial"] ?: "",
                cargaMap["Sabado"] ?: "",
                cargaMap["Viernes"] ?: "",
                cargaMap["Jueves"] ?: "",
                cargaMap["Miercoles"] ?: "",
                cargaMap["Martes"] ?: "",
                cargaMap["Lunes"] ?: "",
                cargaMap["EstadoMateria"] ?: "",
                cargaMap["CreditosMateria"] ?: "",
                cargaMap["Materia"] ?: "",
                cargaMap["Grupo"] ?: ""
            )
        }.toList()
}

