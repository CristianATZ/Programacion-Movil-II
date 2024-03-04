package com.example.accesologin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.accesologin.model.Cardex
import com.example.accesologin.model.CardexProm


@Composable
fun CardexScreen(
    navController: NavController,
    text: String?
){
    var obj = text.toString().split("/")
    val prom = parseCardexProm(obj[0])
    val kardex = parseCardexList(obj[1])
    LazyColumn {
        item {
            if (prom != null) {
                Text(text = "Prom. General: ${prom.PromedioGral}")
            }
        }
        item {
            for(materia in kardex){
                Column {
                    Text(materia.Materia)
                    Text(materia.Calif)
                    Text(materia.Acred)
                }
            }
        }
    }
}

fun parseCardexList(input: String): List<Cardex> {
    val cargaRegex = Regex("Cardex\\((.*?)\\)")

    return cargaRegex
        .findAll(input)
        .map { matchResult ->
            val cardexParams = matchResult.groupValues[1]
            val cardexMap = cardexParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            Cardex(
                cardexMap["S3"] ?: "",
                cardexMap["P3"] ?: "",
                cardexMap["A3"] ?: "",
                cardexMap["ClvMat"] ?: "",
                cardexMap["ClvOfiMat"] ?: "",
                cardexMap["Materia"] ?: "",
                cardexMap["Cdts"] ?: "",
                cardexMap["Calif"] ?: "",
                cardexMap["Acred"] ?: "",
                cardexMap["S1"] ?: "",
                cardexMap["P1"] ?: "",
                cardexMap["A1"] ?: "",
                cardexMap["S2"] ?: "",
                cardexMap["P2"] ?: "",
                cardexMap["A2"] ?: ""

            )
        }.toList()
}

fun parseCardexProm(input: String): CardexProm? {
    val cargaRegex = Regex("CardexProm\\((.*?)\\)")

    return cargaRegex
        .find(input)
        ?.let { matchResult ->
            val cardexParams = matchResult.groupValues[1]
            val cardexMap = cardexParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            CardexProm(
                cardexMap["PromedioGral"] ?: "",
                cardexMap["CdtsAcum"] ?: "",
                cardexMap["CdtsPlan"] ?: "",
                cardexMap["MatCursadas"] ?: "",
                cardexMap["MatAprobadas"] ?: "",
                cardexMap["AvanceCdts"] ?: ""
            )
        }
}