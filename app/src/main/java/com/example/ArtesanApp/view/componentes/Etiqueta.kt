package com.example.ArtesanApp.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ArtesanApp.ui.theme.CelesteClaro
import com.example.ArtesanApp.ui.theme.CelesteOscuro

// Chip pequeño con fondo de color (ej: "Catálogo", "Cerámica", "Disponible")
@Composable
fun Etiqueta(
    texto: String,
    colorFondo: Color = CelesteClaro,
    colorTexto: Color = CelesteOscuro
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(colorFondo)
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = texto,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorTexto
        )
    }
}
