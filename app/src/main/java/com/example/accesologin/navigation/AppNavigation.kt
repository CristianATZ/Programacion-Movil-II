package com.example.accesologin.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.accesologin.ui.AccesoLoginApp
import com.example.accesologin.ui.screens.HomeScreen
import com.example.accesologin.ui.screens.LoginScreen
import com.example.accesologin.ui.screens.LoginViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(AppScreens.AccesoLoginApp.route){
            AccesoLoginApp(navController)
        }
    }
}