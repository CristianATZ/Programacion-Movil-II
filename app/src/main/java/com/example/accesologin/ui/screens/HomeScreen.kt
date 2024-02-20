package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.viewModelLogin

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    text: String?
){
    val estudiante = text?.split("(", ")")?.get(1)?.split(",")
    Scaffold {
        Column(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = "Nombre: "+estudiante?.get(0)?.split("=")?.get(1))
            Text(text = "Semestre actual: " + estudiante?.get(2)?.split("=")?.get(1))
            Text(text = "Creditos acumulados: " + estudiante?.get(3)?.split("=")?.get(1))
            Text(text = "Creditos actuales: " + estudiante?.get(4)?.split("=")?.get(1))
            Text(text = "Carrera: " + estudiante?.get(5)?.split("=")?.get(1))
            Text(text = "No. Control: " + estudiante?.get(6)?.split("=")?.get(1))
            Text(text = estudiante?.get(7)?.split("=")?.get(1).toString())
        }
    }
}