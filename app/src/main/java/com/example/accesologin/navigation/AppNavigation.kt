package com.example.accesologin.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.accesologin.ui.screens.HomeScreen
import com.example.accesologin.ui.screens.LoginScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(
            AppScreens.AccesoLoginApp.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            }
        )){
            HomeScreen(navController, it.arguments?.getString("text"))
        }
    }
}