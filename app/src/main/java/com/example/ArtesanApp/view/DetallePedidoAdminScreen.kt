package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.EtiquetaEstado
import com.example.ArtesanApp.componentes.FilaMonto
import com.example.ArtesanApp.componentes.TarjetaConTitulo
import com.example.ArtesanApp.componentes.TotalRemarcado
import com.example.ArtesanApp.componentes.formatearFecha
import com.example.ArtesanApp.componentes.formatearMetodoPago
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.model.EstadoPedido
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.RosadoFuerte
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.viewmodel.PedidosAdminViewModel

@Composable
fun DetallePedidoAdminScreen(
    navController: NavController,
    idPedido: Long,
    pedidosAdminViewModel: PedidosAdminViewModel = viewModel()
) {
    val pedidos by pedidosAdminViewModel.pedidos.collectAsState()
    val guardando by pedidosAdminViewModel.guardando.collectAsState()
    val mensajeServidor by pedidosAdminViewModel.mensaje.collectAsState()

    val pedido = pedidos.find { it.id == idPedido }

    var estadoElegido by remember { mutableStateOf<EstadoPedido?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mensajeServidor) {
        if (mensajeServidor != "") {
            snackbarHostState.showSnackbar(mensajeServidor)
            pedidosAdminViewModel.mensajeMostrado()
        }
    }

    Scaffold(
        topBar = { BarraSuperior() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                Text(text = "Volver a pedidos", color = TextoOscuro, fontWeight = FontWeight.Bold)
            }

            if (pedido == null) {
                Text(text = "No se encontró el pedido.", color = TextoSecundario)
                return@Column
            }

            val siguiente = pedidosAdminViewModel.siguienteEstado(pedido.estado)

            Spacer(modifier = Modifier.height(8.dp))

            Encabezado(
                etiqueta = "Pedido N° ${pedido.id}",
                titulo = "Detalle del pedido"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                EtiquetaEstado(estado = pedido.estado)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = formatearFecha(pedido.fecha), color = TextoSecundario)
            }

            Spacer(modifier = Modifier.height(20.dp))

            TarjetaConTitulo(titulo = "Cliente") {
                Text(text = pedido.clienteNombre, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text(text = pedido.clienteEmail, color = TextoSecundario)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Dirección de despacho", fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text(text = pedido.direccionDespacho, color = TextoSecundario)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TarjetaConTitulo(titulo = "Productos") {

                for (detalle in pedido.detalles) {
                    FilaMonto(
                        texto = "${detalle.productoNombre} × ${detalle.cantidad}",
                        monto = formatearPrecio(detalle.subtotal)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 8.dp))

                FilaMonto(texto = "Método de pago", monto = formatearMetodoPago(pedido.metodoPago))

                Spacer(modifier = Modifier.height(16.dp))

                TotalRemarcado(total = pedido.total)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (siguiente == null) {
                Text(text = "Este pedido ya no puede cambiar de estado.", color = TextoSecundario)
            } else {
                Button(
                    onClick = { estadoElegido = siguiente },
                    enabled = !guardando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Celeste,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Pasar a ${siguiente.texto}", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { estadoElegido = EstadoPedido.CANCELADA },
                    enabled = !guardando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(text = "Cancelar pedido", color = RosadoFuerte, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    val nuevoEstado = estadoElegido

    if (pedido != null && nuevoEstado != null) {
        AlertDialog(
            onDismissRequest = { estadoElegido = null },
            title = { Text("Cambiar estado") },
            text = {
                if (nuevoEstado == EstadoPedido.CANCELADA) {
                    Text("El pedido N° ${pedido.id} quedará cancelado y se devolverá el stock. ¿Confirmas?")
                } else {
                    Text("El pedido N° ${pedido.id} pasará a ${nuevoEstado.texto}. ¿Confirmas?")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        pedidosAdminViewModel.cambiarEstado(pedido, nuevoEstado)
                        estadoElegido = null
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { estadoElegido = null }) {
                    Text("Volver")
                }
            }
        )
    }
}
