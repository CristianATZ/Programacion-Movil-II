package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
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
import com.example.accesologin.model.Cardex
import com.example.accesologin.model.CardexProm
import com.example.accesologin.model.Cardex_Entity
import com.example.accesologin.model.Carga
import com.example.accesologin.viewmodel.AlumnoViewModel
import com.example.accesologin.viewmodel.LoginViewModel
import com.example.accesologin.viewmodel.WorkerCardexState
import com.example.accesologin.viewmodel.WorkerCargaState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CardexScreen(
    navController: NavController,
    text: String?,
    viewModelAcademic: AlumnoViewModel = viewModel(factory = AlumnoViewModel.Factory),
    viewModelLogin: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){
    var obj = text.toString()
    if(obj.contains("~")){
        obj = text.toString().split("~")[1]
    }
    //val prom = parseCardexProm(obj[0])
    val kardex = parseCardexList(obj)
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiStateCardex by viewModelAcademic.workerUiStateCardex.collectAsStateWithLifecycle()

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
                                drawerState.apply {
                                    if(isClosed) open()
                                    else close()
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
                            text = "Cárdex",
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
                    when(uiStateCardex){
                        is WorkerCardexState.Default -> {
                            "No se ha sincronizado con SICENet"
                        }
                        is WorkerCardexState.Loading -> {
                            "Sincronizando con SICENet..."
                        }
                        is WorkerCardexState.Complete -> {
                            if(conexionInternet(context)) "Última actualización: " + SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(
                                Date()
                            )
                            else "Última actualización: " + kardex[0].fecha
                        }
                    }
                    )
                }
                /*
                item {
                    if (prom != null) {
                        Text(
                            text = "Prom. General: ${prom.PromedioGral}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, top = 5.dp, end = 15.dp, bottom = 5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }
                }*/
                item {
                    for(materia in kardex){
                        CardCardex(materia)
                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun parseCardexList(input: String): List<Cardex_Entity> {
    Log.d("CardexScreen", input)
    val cargaRegex =
        if(input.contains("Cardex_Entity")){
            Regex("Cardex_Entity\\((.*?)\\)")
        } else {
            Regex("Cardex\\((.*?)\\)")
        }


    return cargaRegex
        .findAll(input)
        .map { matchResult ->
            val cardexParams = matchResult.groupValues[1]
            val cardexMap = cardexParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            Cardex_Entity(
                ClvMat = cardexMap["ClvMat"] ?: "",
                ClvOfiMat = cardexMap["ClvOfiMat"] ?: "",
                Materia = cardexMap["Materia"] ?: "",
                Cdts = cardexMap["Cdts"] ?: "",
                Calif = cardexMap["Calif"] ?: "",
                Acred = cardexMap["Acred"] ?: "",
                S1 = cardexMap["S1"] ?: "",
                P1 = cardexMap["P1"] ?: "",
                A1 = cardexMap["A1"] ?: "",
                S2 = cardexMap["S2"] ?: "",
                P2 = cardexMap["P2"] ?: "",
                A2 = cardexMap["A2"] ?: "",
                S3 = cardexMap["S3"] ?: "",
                P3 = cardexMap["P3"] ?: "",
                A3 = cardexMap["A3"] ?: "",
                fecha = cardexMap["fecha"] ?: SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
            )
        }.toList()
}

fun parseCardexProm(input: String): CardexProm? {
    val cargaRegex = Regex("CardexProm\\((.*?)\\)")

    return cargaRegex
        .find(input)
        ?.let { matchResult ->
            val cardexParams = matchResult.groupValues[1]
            val cardexMap = cardexParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            CardexProm(
                cardexMap["PromedioGral"] ?: "",
                cardexMap["CdtsAcum"] ?: "",
                cardexMap["CdtsPlan"] ?: "",
                cardexMap["MatCursadas"] ?: "",
                cardexMap["MatAprobadas"] ?: "",
                cardexMap["AvanceCdts"] ?: ""
            )
        }
}

@Composable
fun CardCardex(materia: Cardex_Entity){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .height(70.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    text = materia.Calif
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = materia.Materia)
                Text("Acreditación: " + materia.Acred)
            }
        }
    }
}