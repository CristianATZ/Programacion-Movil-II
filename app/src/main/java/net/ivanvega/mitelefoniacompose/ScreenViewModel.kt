package net.ivanvega.mitelefoniacompose

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
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

    fun EnviarMensaje(numero: String) {
        val manager = SmsManager.getDefault()
        // mandar mensaje al numero
        Log.d("EnviarMensaje","si jalo")
        if(_telefono.value.equals(numero)){
            manager.sendTextMessage(_telefono.value,null,_mensaje.value,null,null)
        }
    }
}