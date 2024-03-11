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
    override fun onReceive(p0: Context?, intent: Intent?) {
        val action: String? = intent?.action
        if (action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            Log.d("ESTADO", action.toString())
        }
    }

}