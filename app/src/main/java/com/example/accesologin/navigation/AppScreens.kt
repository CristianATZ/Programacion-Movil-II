package com.example.accesologin.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login_screen")
    object HomeScreen: AppScreens("home_screen")
    object AcademicScheduleScreen: AppScreens("academic_schedule_screen")
    object CardexScreen: AppScreens("cardex_screen")
    object FinalsCalifScreen: AppScreens("finals_calif_screen")
    object UnitsCalifScreen: AppScreens("units_calif_screen")
}