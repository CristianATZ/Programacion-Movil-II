package com.example.accesologin.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.accesologin.ui.screens.HomeScreen
import androidx.navigation.NavController
import com.example.accesologin.ui.screens.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccesoLoginApp(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            HomeScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                contentPadding = it
            )
        }
    }
}