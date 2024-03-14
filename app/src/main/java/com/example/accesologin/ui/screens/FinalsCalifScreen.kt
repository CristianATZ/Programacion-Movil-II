package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.model.CalifFinal
import com.example.accesologin.model.CalifFinal_Entity
import com.example.accesologin.model.Carga
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.AlumnoViewModel
import com.example.accesologin.viewmodel.LoginViewModel
import com.example.accesologin.viewmodel.WorkerCargaState
import com.example.accesologin.viewmodel.WorkerFinalsState
import com.example.accesologin.viewmodel.WorkerUnitsState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FinalsCalifScreen(
    navController: NavController,
    text: String?,
    viewModelAcademic: AlumnoViewModel = viewModel(factory = AlumnoViewModel.Factory),
    viewModelLogin: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){ 
    val califFinales = parseCalifList(text.toString())
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiStateFinales by viewModelAcademic.workerUiStateFinals.collectAsStateWithLifecycle()

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
                                if(conexionInternet(context)){
                                    obtenerInfo(viewModelLogin, navController)
                                } else {
                                    obtenerInfoDB(viewModelLogin, navController)
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
                                if(conexionInternet(context)){
                                    obtenerCargaAcademica(viewModelAcademic, navController)
                                } else {
                                    obtenerCargaAcademicaDB(viewModelAcademic, navController)
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Cardex") },
                        icon = { Icon(Icons.Outlined.HistoryEdu, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                if(conexionInternet(context)){
                                    obtenerKardexConPromedioByAlumno(viewModelAcademic, navController)
                                } else {
                                    obtenerKardexConPromedioByAlumnoDB(viewModelAcademic, navController)
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones por unidad") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                if(conexionInternet(context)){
                                    obtenerCalificaciones(viewModelAcademic, navController)
                                } else {
                                    obtenerCalificacionesDB(viewModelAcademic, navController)
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones finales") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                if(conexionInternet(context)){
                                    obtenerCalifFinales(viewModelAcademic, navController)
                                } else {
                                    obtenerCalifFinalesDB(viewModelAcademic, navController)
                                }
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
                            text = "Calificaciones Finales",
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) {
            LazyColumn{
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
                item {
                    Text(text =
                        when(uiStateFinales){
                            is WorkerFinalsState.Default -> {
                                "No se ha sincronizado con SICENet"
                            }
                            is WorkerFinalsState.Loading -> {
                                "Sincronizando con SICENet..."
                            }
                            is WorkerFinalsState.Complete -> {
                                if(conexionInternet(context)) "Última actualización: " + SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(
                                    Date()
                                )
                                else "Última actualización: " + califFinales[0].fecha
                            }
                        }
                    )
                }
                item {
                    for(calif in califFinales){
                        CardFinals(calif)
                    }
                }
            }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
fun parseCalifList(input: String): List<CalifFinal_Entity> {
    val califRegex =
        if(input.contains("CalifFinal_Entity")){
            Regex("CalifFinal_Entity\\((.*?)\\)")
        } else {
            Regex("CalifFinal\\((.*?)\\)")
        }


    return califRegex
        .findAll(input)
        .map { matchResult ->
            val califParams = matchResult.groupValues[1]
            val califMap = califParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            CalifFinal_Entity(
                calif = califMap["calif"] ?: "",
                acred = califMap["acred"] ?: "",
                grupo = califMap["grupo"] ?: "",
                materia = califMap["materia"] ?: "",
                Observaciones = califMap["Observaciones"] ?: "",
                fecha = califMap["fecha"] ?: SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
            )
        }.toList()
}


@Composable
fun CardFinals(calif: CalifFinal_Entity){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                text = calif.materia
            )
            Text("Grupo: " + calif.grupo)
            if(calif.calif.equals("0")) Text("Sin calificación")
            else Text("Calificacion: " + calif.calif)
            Text("Acreditación: " + calif.acred)
        }
    }
}