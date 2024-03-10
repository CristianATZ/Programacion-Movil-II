package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.AlumnoViewModel
import com.example.accesologin.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    text: String?,
    viewModelAcademic: AlumnoViewModel = viewModel(factory = AlumnoViewModel.Factory)
){
    val estudiante = text?.split("(", ")")?.get(1)?.split(",")

    var openCloseSesion by remember {
        mutableStateOf(false)
    }

    if(openCloseSesion){
        LaunchedEffect(openCloseSesion){
            delay(3000)
            openCloseSesion = false
            navController.navigate(AppScreens.LoginScreen.route)
        }
        DialogCloseSesion()
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Menu",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    val scope = rememberCoroutineScope()
                    NavigationDrawerItem(
                        label = { Text("Info Básica") },
                        icon = { Icon(Icons.Outlined.Info, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if(isClosed) open()
                                    else close()
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Carga Académica") },
                        icon = { Icon(Icons.Outlined.Schedule, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCargaAcademica(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Cardex") },
                        icon = { Icon(Icons.Outlined.HistoryEdu, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerKardexConPromedioByAlumno(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones por unidad") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCalificaciones(viewModelAcademic, navController)
                            }
                            //navController.navigate(AppScreens.UnitsCalifScreen.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones finales") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCalifFinales(viewModelAcademic, navController)
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Button(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if(isClosed) open()
                                        else close()
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    title = { 
                        Text(
                            text = "Bienvenido",
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor =MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(7.dp, top = 40.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.size(32.dp))

                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = estudiante?.get(0)?.split("=")?.get(1).toString().substring(0,1),
                        color = MaterialTheme.colorScheme.background,
                        fontWeight = FontWeight.Bold,
                        fontSize = 64.sp
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = estudiante?.get(0)?.split("=")?.get(1).toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = estudiante?.get(5)?.split("=")?.get(1).toString(),
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = estudiante?.get(7)?.split("=")?.get(1).toString())

                Spacer(modifier = Modifier.size(32.dp))
                Divider()
                Spacer(modifier = Modifier.size(32.dp))


                AtributoAlumno("Semestre actual:", estudiante?.get(2)?.split("=")?.get(1).toString())

                AtributoAlumno("Creditos acumualdos:", estudiante?.get(3)?.split("=")?.get(1).toString())

                AtributoAlumno("No. Control:", estudiante?.get(6)?.split("=")?.get(1).toString())

                Spacer(modifier = Modifier.size(32.dp))
                Divider()
                Spacer(modifier = Modifier.size(32.dp))


                Button(
                    onClick = {
                        openCloseSesion = !openCloseSesion
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.9f)
                        .padding()
                ) {
                    Text(text = "Cerrar sesion", modifier = Modifier.padding(PaddingValues(4.dp)))
                }
            }
        }
    }
}

@Composable
fun DialogCloseSesion() {
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
                    text = "Cerrando sesion...",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun AtributoAlumno(header: String, body: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(PaddingValues(8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = header)
        Text(
            text = body,
            fontWeight = FontWeight.Bold
        )
    }
}

suspend fun obtenerCargaAcademica(viewModel: AlumnoViewModel, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerCargaAcademica")
    var schedule = viewModel.getAcademicSchedule()
    var encodedInfo = Uri.encode(schedule)
    navController.navigate(AppScreens.AcademicScheduleScreen.route + encodedInfo)
}

suspend fun obtenerCalificaciones(viewModel: AlumnoViewModel, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerCalififcaciones")
    var unidades = viewModel.getCalifByUnidad()
    var encodedInfo = Uri.encode(unidades)
    navController.navigate(AppScreens.UnitsCalifScreen.route + encodedInfo)
}

suspend fun obtenerCalifFinales(viewModel: AlumnoViewModel, navController: NavController){
    var unidades = viewModel.getCalifFinal()
    var encodedInfo = Uri.encode(unidades)
    navController.navigate(AppScreens.FinalsCalifScreen.route + encodedInfo)
}

suspend fun obtenerKardexConPromedioByAlumno(viewModel: AlumnoViewModel, navController: NavController){
    var cardex = viewModel.getCardexByAlumno()
    var encodedInfo = Uri.encode(cardex)
    navController.navigate(AppScreens.CardexScreen.route + encodedInfo)
}