package com.example.ArtesanApp.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario

// Una fila con un texto a la izquierda y un monto a la derecha (ej: "IVA (19%)    $2.539")
@Composable
fun FilaMonto(texto: String, monto: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = texto, color = TextoSecundario, modifier = Modifier.weight(1f))
        Text(text = monto, fontWeight = FontWeight.Bold, color = TextoOscuro)
    }
}
