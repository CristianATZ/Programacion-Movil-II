package com.example.accesologin.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login_screen")
    object AccesoLoginApp: AppScreens("acceso_login_app")
}