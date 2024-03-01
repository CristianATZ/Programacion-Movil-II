package com.example.accesologin.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.viewModelLogin
import kotlinx.coroutines.launch

@Composable
fun AcademicScheduleScreen(
    navController: NavController,
    text: String?
){
    val carga = text?.split("(", ")")?.get(1)?.split(",")
    Text(text = carga?.get(0)?.split("=")?.get(1).toString())
    Text(text = carga?.get(1)?.split("=")?.get(1).toString())
}

