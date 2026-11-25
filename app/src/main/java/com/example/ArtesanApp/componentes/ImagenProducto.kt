package com.example.ArtesanApp.componentes

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.ArtesanApp.R

@Composable
fun ImagenProducto(
    categoria: String,
    modifier: Modifier = Modifier
) {
    val imagen = when (categoria) {
        "Cerámica" -> R.drawable.ceramica
        "Textil" -> R.drawable.textil
        "Madera" -> R.drawable.madera
        "Joyería" -> R.drawable.joyeria
        "Cestería" -> R.drawable.cesteria
        else -> R.drawable.artesania
    }

    Image(
        painter = painterResource(imagen),
        contentDescription = categoria,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
