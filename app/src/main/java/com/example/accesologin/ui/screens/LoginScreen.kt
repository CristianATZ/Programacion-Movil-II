package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.AlumnosContainer
import com.example.accesologin.model.Acceso_Entity
import com.example.accesologin.model.Alumno_Entity
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){
    val _padding = 15.dp
    val context = LocalContext.current

    var seePass by remember {
        mutableStateOf(false)
    }
    
    var openError by remember {
        mutableStateOf(false)
    }

    var openAcces by remember {
        mutableStateOf(false)
    }

    var openNoInternet by remember {
        mutableStateOf(false)
    }

    if(openError){
        DialogError(){
            openError = !openError
            openAcces = false
        }
    }

    if(openNoInternet){
        DialogNOInternet {
            openNoInternet = !openNoInternet
            openAcces = false
        }
    }


    if(openAcces){
        LaunchedEffect(openAcces){
            delay(3000)
            openAcces = !openAcces
        }
        DialogAcces()
    }




    Scaffold(
        topBar ={
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "SICEnet",
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
                    value = viewModel.matricula,
                    onValueChange = {
                        viewModel.updateMatricula(it)
                    },
                    label = { Text("Numero de control") }
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(_padding),
                    value = viewModel.password,
                    onValueChange = {
                        viewModel.updatePassword(it)
                    },
                    label = {
                        Text("Contraseña")
                    },
                    visualTransformation = if(seePass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { seePass = !seePass }) {
                            Icon(
                                imageVector = if (seePass) Icons.Outlined.Lock else Icons.Outlined.LockOpen,
                                contentDescription = null
                            )
                        }
                    }
                )
                val scope = rememberCoroutineScope()
                Button(
                    onClick = {
                        openAcces = !openAcces
                        if(validacion(viewModel)){
                            if(conexionInternet(context)){
                                scope.launch {
                                    if(obtenerAcceso(viewModel)){
                                        runBlocking { delay(2000) }
                                        viewModel.updateMatricula("")
                                        viewModel.updatePassword("")
                                        obtenerInfo(viewModel, navController)
                                    } else {
                                        openError = !openError
                                    }
                                }
                            }
                            else {
                                // openNoInternet = !openNoInternet
                                scope.launch {
                                    val acceso = viewModel.getAccessDB(viewModel.matricula, viewModel.password)

                                    if(acceso){
                                        obtenerInfoDB(viewModel, navController)
                                    }
                                    else{
                                        openNoInternet = !openNoInternet
                                    }
                                }
                            }
                        } else {
                            openError = !openError
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(_padding)
                ) {
                    Text(text = "INGRESAR", modifier = Modifier.padding(PaddingValues(4.dp)))
                }
            }
        }
    }
}

@Composable
fun DialogAcces() {
    Dialog(onDismissRequest = { }) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "Iniciando sesion...",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun DialogError(onClick: () -> Unit) {
    Dialog(onDismissRequest = { onClick() }) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .size(80.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Datos erroneos mano!!!",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}


@Composable
fun DialogNOInternet(onClick: () -> Unit) {
    Dialog(onDismissRequest = { onClick() }) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .size(80.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "No hay conexión a internet",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}


private fun validacion(viewModel: LoginViewModel): Boolean {
    return !viewModel.matricula.equals("") && !viewModel.password.equals("")
}

fun conexionInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if(connectivityManager != null){
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if(capabilities != null){
            if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                return true
            }
            else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return true
            }
            else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                return true
            }
        }
    }
    return false
}

suspend fun obtenerAcceso(viewModel: LoginViewModel): Boolean {
    //val TAG = "LOGIN SCREEN"
    //Log.d(TAG, viewModel.getAccess(viewModel.matricula, viewModel.password).toString())
    return viewModel.getAccess(viewModel.matricula, viewModel.password)
}

suspend fun obtenerInfo(viewModel: LoginViewModel, navController: NavController){
    var info = viewModel.getInfo()
    var encodedInfo = Uri.encode(info)
    // INVOCACION DEL WORKER
    viewModel.guardadoWorker()
    navController.navigate(AppScreens.HomeScreen.route + encodedInfo)
}

suspend fun obtenerInfoDB(viewModel: LoginViewModel, navController: NavController){
    // MANDAR INFO POR AQUI
    var info = viewModel.getInfoDB()
    Log.d("noInternet",info)
    var encodedInfo = Uri.encode(info)
    navController.navigate(AppScreens.HomeScreen.route + encodedInfo)
}