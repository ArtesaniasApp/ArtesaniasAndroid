package com.example.ArtesanApp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.EtiquetaEstado
import com.example.ArtesanApp.componentes.ImagenProducto
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.model.Solicitud
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.Marino
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.viewmodel.SolicitudViewModel
import com.example.ArtesanApp.navigation.Routes

// Mis solicitudes: lista de las compras del cliente. Solo se ven, el cliente no puede cancelarlas
@Composable
fun SolicitudesScreen(
    navController: NavController,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    val solicitudes by solicitudViewModel.solicitudes.collectAsState()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, Routes.SOLICITUDES) },
        containerColor = Fondo
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {

            Encabezado(
                etiqueta = "Mis compras",
                titulo = "Mis solicitudes",
                subtitulo = "Revisa el estado de tus solicitudes de compra."
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (solicitudes.isEmpty()) {
                Text(text = "Aún no tienes solicitudes de compra.", color = TextoSecundario)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // reversed(): la más nueva arriba
                    items(solicitudes.reversed()) { solicitud ->
                        TarjetaSolicitud(
                            solicitud = solicitud,
                            onClick = { navController.navigate("${Routes.SOLICITUD}/${solicitud.numero}") }
                        )
                    }
                }
            }
        }
    }
}

// Tarjeta de una solicitud: imagen del primer producto, número, fecha, estado y total
@Composable
fun TarjetaSolicitud(
    solicitud: Solicitud,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borde)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del primer producto que se agregó al carro
            ImagenProducto(
                categoria = solicitud.detalles.first().categoria,
                tamanoIcono = 36.sp,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(14.dp))
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Solicitud N° ${solicitud.numero}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Marino
                )
                Text(text = solicitud.fecha, fontSize = 13.sp, color = TextoSecundario)

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EtiquetaEstado(estado = solicitud.estado)

                    Text(
                        text = formatearPrecio(solicitud.total),
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                }
            }
        }
    }
}
