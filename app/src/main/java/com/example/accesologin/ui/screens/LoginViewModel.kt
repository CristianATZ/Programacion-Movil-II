package com.example.accesologin.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesologin.network.LoginApi
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    var loginUiState: String by mutableStateOf("")
        private set

    init {
        getAlumno()
    }

    private fun getAlumno() {
        viewModelScope.launch {
            val result = LoginApi.retrofitService.getAlumno()
            loginUiState = result
        }
    }

}