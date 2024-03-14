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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.model.CalifFinal
import com.example.accesologin.model.CalifUnidad_Entity
import com.example.accesologin.model.CalificacionByUnidad
import com.example.accesologin.model.Carga
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.AlumnoViewModel
import com.example.accesologin.viewmodel.LoginViewModel
import com.example.accesologin.viewmodel.WorkerCargaState
import com.example.accesologin.viewmodel.WorkerUnitsState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnitsCalifScreen(
    navController: NavController,
    text: String?,
    viewModelAcademic: AlumnoViewModel = viewModel(factory = AlumnoViewModel.Factory),
    viewModelLogin: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){
    val calificaciones = parseUnidadList(text.toString())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiStateUnidades by viewModelAcademic.workerUiStateUnits.collectAsStateWithLifecycle()

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
                            text = "Calificaciones Por Unidad",
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
                        when(uiStateUnidades){
                            is WorkerUnitsState.Default -> {
                                "No se ha sincronizado con SICENet"
                            }
                            is WorkerUnitsState.Loading -> {
                                "Sincronizando con SICENet..."
                            }
                            is WorkerUnitsState.Complete -> {
                                if(conexionInternet(context)) "Última actualización: " + SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(
                                    Date()
                                )
                                else "Última actualización: " + calificaciones[0].fecha
                            }
                        }
                    )
                }
                item {
                    for(calif in calificaciones){
                        CardCalifs(calif)
                    }
                }
            }
        }
    }
    

}

@RequiresApi(Build.VERSION_CODES.O)
fun parseUnidadList(input: String): List<CalifUnidad_Entity> {
    val regex =
        if(input.contains("CalifUnidad_Entity")){
            Regex("CalifUnidad_Entity\\((.*?)\\)")
        } else {
            Regex("CalificacionByUnidad\\((.*?)\\)")
        }

    return regex
        .findAll(input)
        .map { matchResult ->
            val params = matchResult.groupValues[1]
            val map = params.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })

            CalifUnidad_Entity(
                Observaciones = map["Observaciones"] ?: "",
                C13 = map["C13"] ?: "",
                C12 = map["C12"] ?: "",
                C11 = map["C11"] ?: "",
                C10 = map["C10"] ?: "",
                C9 = map["C9"] ?: "",
                C8 = map["C8"] ?: "",
                C7 = map["C7"] ?: "",
                C6 = map["C6"] ?: "",
                C5 = map["C5"] ?: "",
                C4 =  map["C4"] ?: "",
                C3 = map["C3"] ?: "",
                C2 = map["C2"] ?: "",
                C1 = map["C1"] ?: "",
                UnidadesActivas = map["UnidadesActivas"] ?: "",
                Materia = map["Materia"] ?: "",
                Grupo =  map["Grupo"] ?: "",
                fecha = map["fecha"] ?: SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
            )
        }.toList()
}


@Composable
fun CardCalifs(calif: CalifUnidad_Entity){
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
                text = calif.Materia
            )
            if(!calif.C1.equals("null")) Text("Unidad 1: " + calif.C1)
            if(!calif.C2.equals("null")) Text("Unidad 2: " + calif.C2)
            if(!calif.C3.equals("null")) Text("Unidad 3: " + calif.C3)
            if(!calif.C4.equals("null")) Text("Unidad 4: " + calif.C4)
            if(!calif.C5.equals("null")) Text("Unidad 5: " + calif.C5)
            if(!calif.C6.equals("null")) Text("Unidad 6: " + calif.C6)
            if(!calif.C7.equals("null")) Text("Unidad 7: " + calif.C7)
            if(!calif.C8.equals("null")) Text("Unidad 8: " + calif.C8)
            if(!calif.C9.equals("null")) Text("Unidad 9: " + calif.C9)
            if(!calif.C10.equals("null")) Text("Unidad 10: " + calif.C10)
            if(!calif.C11.equals("null")) Text("Unidad 11: " + calif.C11)
            if(!calif.C12.equals("null")) Text("Unidad 12: " + calif.C12)
            if(!calif.C13.equals("null")) Text("Unidad 13: " + calif.C13)
        }
    }
}