package com.example.accesologin.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesologin.data.Repository
import com.example.accesologin.model.AccesoLoginRequest
import com.example.accesologin.model.SoapEnvelope
import com.example.accesologin.network.repository.LoginServiceFactory.retrofitService
import com.example.accesologin.network.repository.bodyAcceso
import com.example.accesologin.network.repository.bodyPerfil
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.awaitResponse

/*
class LoginViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState(""))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = LoginUiState("Cargando...")
        }
    }

    /*
    CRISTIAN
    S20120154
    8s_RH-

    ALAN
    S20120202
    7Sf_/r6Q
     */

    fun getAuth(
        matricula: String,
        contrasenia: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                // convertir la cadena XML a un RequestBody
                val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), String.format(
                    bodyAcceso, matricula, contrasenia))

                // realizar la solicitud
                val response = retrofitService.accesoLogin(requestBody).awaitResponse()


                _uiState.update { current ->
                    current.copy("false")
                }

                // Manejar la respuesta
                if (response.isSuccessful) {

                    val result = response.body()?.string()

                    _uiState.update { current ->
                        current.copy(result.toString())
                    }

                    // Procesar el resultado según sea necesario
                    val resultBody = RequestBody.create("text/xml".toMediaTypeOrNull(), bodyPerfil)

                    val resultPerfil = retrofitService.getPerfilAcademico(resultBody).awaitResponse()

                    if(resultPerfil.isSuccessful){
                        // manejar la informacion
                    } else {

                    }

                } else {
                    _uiState.update { current ->
                        current.copy("false")
                    }
                }

            } catch (e: Exception) {
                _uiState.update { current ->
                    current.copy("false")
                }
            }
        }
    }

    fun convertXmlToJson(xmlString: String): JsonObject{
        try {
            var xmlProcessed = xmlString.substringAfter("<accesoLoginResult>")
            xmlProcessed = xmlProcessed.substringBefore("</accesoLoginResult>")
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonElement = JsonParser().parse(xmlProcessed)
            val jsonObject = jsonElement.asJsonObject
            return jsonObject
        } catch (e: JsonSyntaxException){
            e.printStackTrace()
            return JsonObject()
        }
    }

}

 */

class LoginViewModel(private val alumnosRepository: Repository): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(""))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = LoginUiState("Cargando...")
        }
    }

    suspend fun getAccess(matricula: String, password: String, tipoUsuario: String): Boolean {
        return alumnosRepository.getAccess(matricula, password, tipoUsuario)
    }

    suspend fun getInfo(): String {
        val informacion = viewModelScope.async {
            alumnosRepository.getInfo()
        }
        return informacion.await()
    }
}



data class LoginUiState(
    var matricula: String = "",
    val password: String = "",
    var errorLogin: String = "",
    var tipoUsuario: String = ""
)