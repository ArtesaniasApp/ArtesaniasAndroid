package com.example.ArtesanApp.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ArtesanApp.model.EstadoPedido

@Composable
fun EtiquetaEstado(estado: EstadoPedido) {
    val (colorFondo, colorTexto) = when (estado) {
        EstadoPedido.PENDIENTE -> Pair(Color(0xFFFFF1DC), Color(0xFFC2570C))
        EstadoPedido.RECIBIDA -> Pair(Color(0xFFDDF4FA), Color(0xFF327F9F))
        EstadoPedido.EN_PREPARACION -> Pair(Color(0xFFEDE7F6), Color(0xFF5E35B1))
        EstadoPedido.DESPACHADA -> Pair(Color(0xFFE3EAF6), Color(0xFF172D49))
        EstadoPedido.FINALIZADA -> Pair(Color(0xFFE3F4E8), Color(0xFF2E7D32))
        EstadoPedido.CANCELADA -> Pair(Color(0xFFF9D9E5), Color(0xFFD6457A))
    }

    Etiqueta(texto = estado.texto, colorFondo = colorFondo, colorTexto = colorTexto)
}
