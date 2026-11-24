package com.example.ArtesanApp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.EtiquetaEstado
import com.example.ArtesanApp.componentes.FiltroDesplegable
import com.example.ArtesanApp.componentes.formatearFecha
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.model.EstadoPedido
import com.example.ArtesanApp.model.Solicitud
import com.example.ArtesanApp.navigation.Routes
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.Marino
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.viewmodel.PedidosAdminViewModel

@Composable
fun PedidosAdminScreen(
    navController: NavController,
    pedidosAdminViewModel: PedidosAdminViewModel = viewModel()
) {
    var filtro by rememberSaveable {
        mutableStateOf("Todos")
    }

    val pedidos by pedidosAdminViewModel.pedidos.collectAsState()
    val cargando by pedidosAdminViewModel.cargando.collectAsState()
    val mensajeServidor by pedidosAdminViewModel.mensaje.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val pedidosFiltrados = if (filtro == "Todos") {
        pedidos
    } else {
        pedidos.filter { it.estado.texto == filtro }
    }

    LaunchedEffect(Unit) {
        pedidosAdminViewModel.cargarPedidos()
    }

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                TextButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextoOscuro)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Volver al catálogo", color = TextoOscuro, fontWeight = FontWeight.Bold)
                }
            }

            item {
                Encabezado(
                    etiqueta = "Administración",
                    titulo = "Pedidos",
                    subtitulo = "Pedidos mostrados: ${pedidosFiltrados.size}"
                )
            }

            item {
                FiltroDesplegable(
                    opciones = listOf("Todos") + EstadoPedido.entries.map { it.texto },
                    seleccionada = filtro,
                    onSeleccionar = { filtro = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (cargando && pedidos.isEmpty()) {
                item {
                    CircularProgressIndicator()
                }
            }

            if (!cargando && pedidosFiltrados.isEmpty()) {
                item {
                    Text(text = "No hay pedidos para mostrar.", color = TextoSecundario)
                }
            }

            items(
                items = pedidosFiltrados,
                key = { it.id }
            ) { pedido ->
                TarjetaPedidoAdmin(
                    pedido = pedido,
                    onClick = { navController.navigate("${Routes.PEDIDO_ADMIN}/${pedido.id}") }
                )
            }
        }
    }
}

@Composable
fun TarjetaPedidoAdmin(
    pedido: Solicitud,
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
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Pedido N° ${pedido.id}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Marino
            )
            Text(text = pedido.clienteNombre, color = TextoOscuro)
            Text(text = formatearFecha(pedido.fecha), fontSize = 13.sp, color = TextoSecundario)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                EtiquetaEstado(estado = pedido.estado)

                Text(
                    text = formatearPrecio(pedido.total),
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
            }
        }
    }
}
