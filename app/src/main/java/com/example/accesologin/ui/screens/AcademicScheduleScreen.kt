package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accesologin.model.Carga
import com.example.accesologin.model.Carga_Entity
import com.example.accesologin.viewmodel.AlumnoViewModel
import com.example.accesologin.viewmodel.LoginViewModel
import com.example.accesologin.viewmodel.WorkerCargaState
import com.example.accesologin.viewmodel.WorkerInfoState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AcademicScheduleScreen(
    navController: NavController,
    text: String?,
    viewModelAcademic: AlumnoViewModel = viewModel(factory = AlumnoViewModel.Factory),
    viewModelLogin: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
){

    var carga = parseCargaList(text.toString())
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiStateCarga by viewModelAcademic.workerUiStateCarga.collectAsStateWithLifecycle()

    var dayList = listOf("Lun","Mar","Mie","Jue","Vie")

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
                                drawerState.apply {
                                    if(isClosed) open()
                                    else close()
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
                TopBarAcademic(){
                    scope.launch {
                        drawerState.apply {
                            if(isClosed) open()
                            else close()
                        }
                    }
                }
            }
        ) {
            LazyColumn{
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
                item {
                    Text(text =
                        when(uiStateCarga){
                            is WorkerCargaState.Default -> {
                                "No se ha sincronizado con SICENet"
                            }
                            is WorkerCargaState.Loading -> {
                                "Sincronizando con SICENet..."
                            }
                            is WorkerCargaState.Complete -> {
                                if(conexionInternet(context)) "Última actualización: " + SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(
                                    Date()
                                )
                                else "Última actualización: " + carga[0].fecha
                            }
                        }
                    )
                }
                item{
                    for(materia in carga){
                        CardCarga(materia)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAcademic(
    onClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            Button(
                onClick = {
                    onClick()
                }
            ) {
                Icon(Icons.Default.Menu, contentDescription = null)
            }
        },
        title = {
            Text(
                text = "Carga Académica",
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun parseCargaList(input: String): List<Carga_Entity> {
    val cargaRegex =
        if(input.contains("Carga_Entity")){
            Regex("Carga_Entity\\((.*?)\\)")
        } else {
            Regex("Carga\\((.*?)\\)")
        }


    return cargaRegex
        .findAll(input)
        .map { matchResult ->
            val cargaParams = matchResult.groupValues[1]
            val cargaMap = cargaParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            Carga_Entity(
                Semipresencial = cargaMap["Semipresencial"] ?: "",
                Observaciones = cargaMap["Observaciones"] ?: "",
                Docente = cargaMap["Docente"] ?: "",
                clvOficial = cargaMap["clvOficial"] ?: "",
                Sabado = cargaMap["Sabado"] ?: "",
                Viernes = cargaMap["Viernes"] ?: "",
                Jueves = cargaMap["Jueves"] ?: "",
                Miercoles = cargaMap["Miercoles"] ?: "",
                Martes = cargaMap["Martes"] ?: "",
                Lunes = cargaMap["Lunes"] ?: "",
                EstadoMateria = cargaMap["EstadoMateria"] ?: "",
                CreditosMateria = cargaMap["CreditosMateria"] ?: "",
                Materia = cargaMap["Materia"] ?: "",
                Grupo = cargaMap["Grupo"] ?: "",
                fecha = cargaMap["fecha"] ?: SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
            )
        }.toList()
}

@Composable
fun CardCarga(
    materia: Carga_Entity
){
    var open by remember {
        mutableStateOf(false)
    }

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
                .clickable {
                    open = !open
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = materia.Materia,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row {
                Row(
                    modifier = Modifier
                        .weight(0.2f)
                        .height(60.dp)
                        .background(color = MaterialTheme.colorScheme.primaryContainer),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = materia.Grupo,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(0.8f)
                        .height(60.dp)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = materia.Docente,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if(open){
                Column {
                    if (materia.Lunes.isNotBlank()) {
                        WeekdayText("Lunes: ${materia.Lunes}")
                    }
                    if (materia.Martes.isNotBlank()) {
                        WeekdayText("Martes: ${materia.Martes}")
                    }
                    if (materia.Miercoles.isNotBlank()) {
                        WeekdayText("Miércoles: ${materia.Miercoles}")
                    }
                    if (materia.Jueves.isNotBlank()) {
                        WeekdayText("Jueves: ${materia.Jueves}")
                    }
                    if (materia.Viernes.isNotBlank()) {
                        WeekdayText("Viernes: ${materia.Viernes}")
                    }
                }
            }
        }
    }
}

@Composable
fun WeekdayText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
}