package net.ivanvega.mitelefoniacompose

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScreenViewModel: ViewModel() {

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    fun setTe(string: String){
        viewModelScope.launch {
            _telefono.value = string
        }
    }

    fun setMen(string: String){
        viewModelScope.launch {
            _mensaje.value = string
        }
    }

    fun EnviarMensaje(numero: String, context: Context) {
        val manager = SmsManager.getDefault()
        // mandar mensaje al numero
        manager.sendTextMessage("4451573811",null,_telefono.value,null,null)
        Log.d("Mensaje","si jalo")
    }

}