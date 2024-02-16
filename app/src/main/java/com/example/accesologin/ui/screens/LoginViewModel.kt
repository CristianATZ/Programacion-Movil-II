package com.example.accesologin.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesologin.model.AccesoLoginRequest
import com.example.accesologin.model.SoapEnvelope
import com.example.accesologin.network.repository.LoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    var loginUiState: String by mutableStateOf("asd")
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
                // Crear una instancia de la solicitud
                val request = SoapEnvelope(
                    AccesoLoginRequest(matricula, contrasenia, tipoUsuario)
                )

                // Realizar la solicitud
                val response = LoginApi.retrofitService.accesoLogin(request)

                // Manejar la respuesta
                if (response.isSuccessful) {
                    val result = response.body()?.body?.accesoLoginResult
                    loginUiState = "a"
                    // Procesar el resultado según sea necesario
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