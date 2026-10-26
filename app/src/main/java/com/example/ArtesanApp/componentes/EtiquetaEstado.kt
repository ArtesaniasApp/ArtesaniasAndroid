package com.example.ArtesanApp.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ArtesanApp.model.EstadoPedido

// Chip del estado de una solicitud, con un color distinto para cada estado
@Composable
fun EtiquetaEstado(estado: EstadoPedido) {
    // Primer color = fondo del chip, segundo color = texto
    val (colorFondo, colorTexto) = when (estado) {
        EstadoPedido.PENDIENTE -> Pair(Color(0xFFFFF1DC), Color(0xFFC2570C))       // naranjo
        EstadoPedido.RECIBIDA -> Pair(Color(0xFFDDF4FA), Color(0xFF327F9F))        // celeste
        EstadoPedido.EN_PREPARACION -> Pair(Color(0xFFEDE7F6), Color(0xFF5E35B1))  // morado
        EstadoPedido.DESPACHADA -> Pair(Color(0xFFE3EAF6), Color(0xFF172D49))      // azul marino
        EstadoPedido.FINALIZADA -> Pair(Color(0xFFE3F4E8), Color(0xFF2E7D32))      // verde
        EstadoPedido.CANCELADA -> Pair(Color(0xFFF9D9E5), Color(0xFFD6457A))       // rosado
    }

    Etiqueta(texto = estado.texto, colorFondo = colorFondo, colorTexto = colorTexto)
}
