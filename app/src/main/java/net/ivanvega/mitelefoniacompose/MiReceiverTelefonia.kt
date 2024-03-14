package net.ivanvega.mitelefoniacompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.SignalStrength
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast


class MiReceiverTelefonia(viewModel: ScreenViewModel) : BroadcastReceiver() {
    var viewModel = viewModel
    var manager: TelephonyManager? = null
    var context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        this.context = context

        if (action.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Extrae el numero que esta llamado



            /// aqui falta recuperar bien el numero
            val numero = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Log.d("Numero",numero.toString())
            // Envia el mensaje
            if (numero != null) {
                // mandar mensaje al numero
                viewModel.EnviarMensaje(numero)
            }
        }
    }

    // detectar si el celular tiene senial
}