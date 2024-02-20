package com.example.accesologin.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.accesologin.navigation.AppScreens
import com.example.accesologin.viewmodel.viewModelLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    text: String?
){
    val estudiante = text?.split("(", ")")?.get(1)?.split(",")

    var openCloseSesion by remember {
        mutableStateOf(false)
    }

    if(openCloseSesion){
        LaunchedEffect(openCloseSesion){
            delay(3000)
            openCloseSesion = false
            navController.popBackStack()
        }
        DialogCloseSesion()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(7.dp)
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
