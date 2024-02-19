package com.example.accesologin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.accesologin.navigation.AppScreens

@Composable
fun HomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    ResultScreen(
        loginViewModel,
        modifier.padding(top = contentPadding.calculateTopPadding()),
        navController
    )
}

@Composable
fun ResultScreen(loginViewModel: LoginViewModel, modifier: Modifier = Modifier, navController: NavController) {

    val acceso by loginViewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        //Text(text = acceso.acceso)


        if(acceso.acceso.contains("<accesoLoginResult>")){
            val result = loginViewModel.convertXmlToJson(acceso.acceso)
            if(!result.isJsonNull && result.get("acceso").toString().equals("true")){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = result.get("matricula").toString())
                    Text(text = result.get("estatus").toString())
                }
            }
            else{
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Usuario y/o contrasenia incorrecta. Intente de nuevo.")
                    Button(
                        onClick = {
                            navController.navigate(AppScreens.LoginScreen.route)
                        }
                    ) {
                        Text("Volver")
                    }
                }
            }
        } else if(acceso.acceso.equals("Cargando...")){
            Text(text = "Cargando...")
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Usuario y/o contrasenia incorrecta. Intente de nuevo.")
                Button(
                    onClick = {
                        navController.navigate(AppScreens.LoginScreen.route)
                    }
                ) {
                    Text("Volver")
                }
            }
        }


    }
}