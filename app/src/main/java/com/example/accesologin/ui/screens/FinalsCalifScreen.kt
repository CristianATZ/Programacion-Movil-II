package com.example.accesologin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.accesologin.model.CalifFinal
import com.example.accesologin.model.Carga
import com.example.accesologin.navigation.AppScreens


@Composable
fun FinalsCalifScreen(
    navController: NavController,
    text: String?
){ 
    val califFinales = parseCalifList(text.toString())
    
    LazyColumn{
        item { 
            for(calif in califFinales){
                Column {
                    Text(calif.materia)
                    Text("Grupo: " + calif.grupo)
                    Text("Calificacion: " + calif.calif)
                    Text("Acreditacion: " + calif.acred)
                    Text("")
                }
            }
        }
    }

}


fun parseCalifList(input: String): List<CalifFinal> {
    val califRegex = Regex("CalifFinal\\((.*?)\\)")

    return califRegex
        .findAll(input)
        .map { matchResult ->
            val califParams = matchResult.groupValues[1]
            val califMap = califParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            CalifFinal(
                califMap["calif"] ?: "",
                califMap["acred"] ?: "",
                califMap["grupo"] ?: "",
                califMap["materia"] ?: "",
                califMap["Observaciones"] ?: ""
            )
        }.toList()
}