package com.example.ArtesanApp.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.viewmodel.QrViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

// Lector QR (como lo enseñó el profesor): el escáner de Google abre su propia cámara
// y devuelve el texto del código, que se guarda en el QrViewModel y se muestra en pantalla
@Composable
fun QrScreen(
    navController: NavController,
    qrViewModel: QrViewModel = viewModel()
) {

    val context = LocalContext.current

    // Texto del último QR leído ("" si aún no se lee ninguno)
    val contenidoQR by qrViewModel.contenidoQR
        .collectAsStateWithLifecycle()

    // Solo se leen códigos QR (no códigos de barra)
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
            .background(Fondo)
            .safeDrawingPadding()
            .padding(20.dp)
    ) {

        TextButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextoOscuro)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Volver", color = TextoOscuro, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Encabezado(
            etiqueta = "Código QR",
            titulo = "¡Encuéntranos acá!",
            subtitulo = "Escanea el código QR de nuestras artesanías."
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Al tocar el botón se abre la cámara del escáner de Google
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
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Celeste,
                contentColor = Color.White
            )
        ) {
            Icon(Icons.Filled.QrCodeScanner, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Escanear QR", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resultado de la lectura
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Borde)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Contenido del QR:",
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )

                Text(
                    text = contenidoQR.ifEmpty {
                        "Todavía no se ha escaneado ningún código"
                    },
                    color = TextoSecundario
                )
            }
        }
    }
}
