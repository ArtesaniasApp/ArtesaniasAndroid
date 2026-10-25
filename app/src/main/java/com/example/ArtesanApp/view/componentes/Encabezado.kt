package com.example.ArtesanApp.view.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ArtesanApp.ui.theme.Marino
import com.example.ArtesanApp.ui.theme.TextoSecundario

// Inicio de cada pantalla: chip + título en serif + subtítulo opcional
@Composable
fun Encabezado(
    etiqueta: String,
    titulo: String,
    subtitulo: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Etiqueta(texto = etiqueta)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = titulo,
            fontSize = 30.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Marino
        )

        if (subtitulo.isNotBlank()) {

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitulo,
                fontSize = 15.sp,
                color = TextoSecundario
            )
        }
    }
}
