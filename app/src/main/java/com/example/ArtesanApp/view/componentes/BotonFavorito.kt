package com.example.ArtesanApp.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.ArtesanApp.ui.theme.Rosado
import com.example.ArtesanApp.ui.theme.RosadoFuerte

// Corazón rosado: relleno si es favorito, solo borde si no lo es
@Composable
fun BotonFavorito(
    esFavorito: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Rosado.copy(alpha = 0.5f))
            .border(1.dp, Rosado, RoundedCornerShape(12.dp))
    ) {
        Icon(
            imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
            tint = RosadoFuerte
        )
    }
}
