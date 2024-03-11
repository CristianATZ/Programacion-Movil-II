package net.ivanvega.mitelefoniacompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.SignalStrength
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast


class MiReceiverTelefonia(viewModel: ScreenViewModel) : BroadcastReceiver() {
    var manager: TelephonyManager? = null
    var context: Context? = null
    var _viewModel = viewModel

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        this.context = context

        if (action == TelephonyManager.EXTRA_INCOMING_NUMBER) {
            manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Extrae el numero que esta llamado
            val numero = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            Log.d("Numero",numero.toString())
            // Envia el mensaje
            if (numero != null) {
                // mandar mensaje al numero
                _viewModel.EnviarMensaje(numero,context)
            }
        }
    }

}