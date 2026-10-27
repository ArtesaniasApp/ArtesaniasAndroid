package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.EtiquetaEstado
import com.example.ArtesanApp.componentes.FilaMonto
import com.example.ArtesanApp.componentes.TarjetaConTitulo
import com.example.ArtesanApp.componentes.TotalRemarcado
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.viewmodel.SolicitudViewModel
import com.example.ArtesanApp.navigation.Routes

// Detalle de una solicitud: lista simple de productos × cantidad (sin imágenes), total y método de pago
@Composable
fun DetalleSolicitudScreen(
    navController: NavController,
    numeroSolicitud: Int,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    val solicitudes by solicitudViewModel.solicitudes.collectAsState()

    // Busca la solicitud por su número
    val solicitud = solicitudes.find { it.numero == numeroSolicitud }

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, Routes.SOLICITUDES) },
        containerColor = Fondo
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            TextButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextoOscuro)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Volver a mis solicitudes", color = TextoOscuro, fontWeight = FontWeight.Bold)
            }

            if (solicitud == null) {
                Text(text = "No se encontró la solicitud.", color = TextoSecundario)
                return@Column
            }

            Spacer(modifier = Modifier.height(8.dp))

            Encabezado(
                etiqueta = "Solicitud N° ${solicitud.numero}",
                titulo = "Detalle de la solicitud"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Estado y fecha
            Row(verticalAlignment = Alignment.CenterVertically) {
                EtiquetaEstado(estado = solicitud.estado)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = solicitud.fecha, color = TextoSecundario)
            }

            Spacer(modifier = Modifier.height(20.dp))

            TarjetaConTitulo(titulo = "Productos") {

                // Lista simple: "Nombre × cantidad" y el monto de esa línea
                for (detalle in solicitud.detalles) {
                    FilaMonto(
                        texto = "${detalle.nombreProducto} × ${detalle.cantidad}",
                        monto = formatearPrecio(detalle.precioUnitario * detalle.cantidad)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 8.dp))

                FilaMonto(texto = "Método de pago", monto = solicitud.metodoPago.texto)

                Spacer(modifier = Modifier.height(16.dp))

                TotalRemarcado(total = solicitud.total)
            }
        }
    }
}
