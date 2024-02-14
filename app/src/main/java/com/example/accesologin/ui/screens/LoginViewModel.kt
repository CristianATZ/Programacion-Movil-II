package com.example.accesologin.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesologin.network.LoginApi
import com.example.accesologin.network.LoginDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Retrofit

class LoginViewModel() : ViewModel() {

    var loginUiState: String by mutableStateOf("")
        private set

    init {
        getAlumno()
    }

    private fun getAlumno(
        matricula: String = "s20120154",
        contrasenia: String = "8s_RH-",
        tipoUsuario: String = "ALUMNO"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val authService = LoginApi.retrofitService
                val responseService = authService.getAlumno(
                    LoginDto(
                        matricula,
                        contrasenia,
                        tipoUsuario
                    )
                )

            } catch(e: Exception) {

            }
        }
    }

}