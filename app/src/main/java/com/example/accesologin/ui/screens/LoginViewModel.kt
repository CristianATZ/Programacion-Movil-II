package com.example.accesologin.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesologin.model.AccesoLoginRequest
import com.example.accesologin.model.SoapEnvelope
import com.example.accesologin.network.repository.LoginServiceFactory.retrofitService
import com.example.accesologin.network.repository.bodyAcceso
import com.example.accesologin.network.repository.bodyPerfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.awaitResponse

class LoginViewModel() : ViewModel() {

    var loginUiState: String by mutableStateOf("cargando...")
        private set

    init {
        getAuth()
    }

    private fun getAuth(
        matricula: String = "s20120154",
        contrasenia: String = "8s_RH-",
        tipoUsuario: String = "ALUMNO"
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                // convertir la cadena XML a un RequestBody
                val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), bodyAcceso)

                // realizar la solicitud
                val response = retrofitService.accesoLogin(requestBody).awaitResponse()


                // Manejar la respuesta
                if (response.isSuccessful) {
                    val result = response.body()?.string()
                    loginUiState = result.toString()

                    // Procesar el resultado según sea necesario
                    val resultBody = RequestBody.create("text/xml".toMediaTypeOrNull(), bodyPerfil)

                    val resultPerfil = retrofitService.getPerfilAcademico(resultBody).awaitResponse()

                    if(resultPerfil.isSuccessful){
                        // manejar la informacion
                    } else {

                    }

                } else {
                    // Manejar el error
                    loginUiState = "fallido"
                }

            } catch (e: Exception) {
                // Manejar excepciones
            }
        }
    }

}
