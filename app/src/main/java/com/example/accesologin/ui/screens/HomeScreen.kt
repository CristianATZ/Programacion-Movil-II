package com.example.accesologin.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    ResultScreen(loginViewModel, modifier.padding(top = contentPadding.calculateTopPadding()))
}

@Composable
fun ResultScreen(loginViewModel: LoginViewModel, modifier: Modifier = Modifier) {

    val acceso by loginViewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = acceso.acceso)
    }
}