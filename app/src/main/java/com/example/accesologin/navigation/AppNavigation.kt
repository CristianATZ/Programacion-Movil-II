package com.example.accesologin.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.accesologin.ui.screens.AcademicScheduleScreen
import com.example.accesologin.ui.screens.CardexScreen
import com.example.accesologin.ui.screens.FinalsCalifScreen
import com.example.accesologin.ui.screens.HomeScreen
import com.example.accesologin.ui.screens.LoginScreen
import com.example.accesologin.ui.screens.UnitsCalifScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(
            AppScreens.HomeScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            }
        )){
            HomeScreen(navController, it.arguments?.getString("text"))
        }
        composable(
            AppScreens.AcademicScheduleScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            }
        )){
            AcademicScheduleScreen(navController, it.arguments?.getString("text"))
        }
        composable(AppScreens.CardexScreen.route){
            CardexScreen(navController)
        }
        composable(AppScreens.UnitsCalifScreen.route){
            UnitsCalifScreen(navController)
        }
        composable(AppScreens.FinalsCalifScreen.route){
            FinalsCalifScreen(navController)
        }
    }
}