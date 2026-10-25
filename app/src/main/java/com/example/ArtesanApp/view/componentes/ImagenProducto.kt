package com.example.ArtesanApp.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.ArtesanApp.model.Categoria
import com.example.ArtesanApp.ui.theme.CelesteClaro
import com.example.ArtesanApp.ui.theme.Rosado

// Imagen del producto. Mientras no se usen fotos reales, muestra un degradado con un ícono según la categoría
@Composable
fun ImagenProducto(
    categoria: Categoria,
    modifier: Modifier = Modifier,
    tamanoIcono: TextUnit = 56.sp
) {
    // Al ser enum, el "when" obliga a cubrir todas las categorías (no necesita "else")
    val icono = when (categoria) {
        Categoria.CERAMICA -> "🏺"
        Categoria.TEXTIL -> "🧶"
        Categoria.MADERA -> "🪵"
        Categoria.JOYERIA -> "💍"
        Categoria.CESTERIA -> "🧺"
    }

    Box(
        modifier = modifier.background(Brush.linearGradient(listOf(CelesteClaro, Rosado))),
        contentAlignment = Alignment.Center
    ) {
        Text(text = icono, fontSize = tamanoIcono)
    }
}
