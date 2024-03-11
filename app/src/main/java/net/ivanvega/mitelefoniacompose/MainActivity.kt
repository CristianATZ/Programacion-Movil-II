package net.ivanvega.mitelefoniacompose

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.ivanvega.mitelefoniacompose.ui.theme.MiTelefoniaComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<ScreenViewModel>()
            MiTelefoniaComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: ScreenViewModel) {

    val context = LocalContext.current

    SystemBroadcastReceiver(
        systemAction = TelephonyManager.ACTION_PHONE_STATE_CHANGED,
        onSystemEvent = { intent ->
            // Handle phone state changes here
            val receiver = MiReceiverTelefonia(viewModel)
            if (intent != null) {
                receiver.onReceive(context, intent)
            }
        },
        viewModel = viewModel
    )

    /* Rest of the HomeScreen */

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.telefono.collectAsState().value,
            onValueChange = { viewModel.setTe(it) },
            label = { Text(text = "Telefono") }
        )

        Spacer(modifier = Modifier.size(32.dp))

        TextField(
            value = viewModel.mensaje.collectAsState().value,
            onValueChange = { viewModel.setMen(it) },
            label = { Text(text = "Mensaje") }
        )
    }
}

@Composable
fun SystemBroadcastReceiver(
    systemAction: String,
    onSystemEvent: (intent: Intent?) -> Unit,
    viewModel: ScreenViewModel
) {
    // Grab the current context in this part of the UI tree
    val context = LocalContext.current

    // Safely use the latest onSystemEvent lambda passed to the function
    val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)

    // If either context or systemAction changes, unregister and register again
    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = MiReceiverTelefonia(viewModel)

        context.registerReceiver(broadcast, intentFilter)

        // When the effect leaves the Composition, remove the callback
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}