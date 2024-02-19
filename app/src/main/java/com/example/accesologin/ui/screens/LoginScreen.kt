package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.ui.AccesoLoginApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController
){
    var _matricula by remember { mutableStateOf("") }
    var _password by remember { mutableStateOf("") }
    val _padding = 15.dp

    Scaffold(
        topBar ={
            TopAppBar(
                title = { 
                    Text(
                        text = "Bienvenido",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor =MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(_padding),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier . fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(_padding),
                    value = _matricula,
                    onValueChange = { newText ->
                        _matricula = newText
                    },
                    label = { Text("Numero de control") }
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(_padding),
                    value = _password,
                    onValueChange = { value ->
                        _password = value
                    },
                    label = {
                        Text("Contraseña")
                    }
                )
                Button(
                    onClick = {
                        /*
                        CoroutineScope(Dispatchers.Default).launch{
                            //getCookie()
                            //enviarSoapRequest()
                            //AccesoLoginApp()
                        }
                         */
                        /*
                        val loginViewModel = LoginViewModel()
                        loginViewModel.getAuth()
                         */
                        navController.navigate(AppScreens.AccesoLoginApp.route)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(_padding)
                ) {
                    Text("Ingresar")
                }
            }
        }
    }
}