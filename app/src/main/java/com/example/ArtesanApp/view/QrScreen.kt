import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ArtesanApp.viewmodel.QrViewModel

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun QrScreen(
    qrViewModel: QrViewModel = viewModel()
) {

    val context = LocalContext.current

    val contenidoQR by qrViewModel.contenidoQR
        .collectAsStateWithLifecycle()

    val opciones = remember {
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }

    val scanner = remember(context, opciones) {
        GmsBarcodeScanning.getClient(context, opciones)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Lector de Código QR",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scanner.startScan()
                    .addOnSuccessListener { barcode ->

                        val contenido = barcode.rawValue.orEmpty()

                        qrViewModel.guardarQR(contenido)
                    }
                    .addOnCanceledListener {
                        // Escaneo cancelado
                    }
                    .addOnFailureListener { error ->

                        Toast.makeText(
                            context,
                            "Error: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        ) {
            Text("Escanear QR")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Contenido del QR:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = contenidoQR.ifEmpty {
                "Todavía no se ha escaneado ningún código"
            }
        )
    }
}
