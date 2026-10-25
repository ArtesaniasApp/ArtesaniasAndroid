package com.example.ArtesanApp.view.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Naranjo
import com.example.ArtesanApp.ui.theme.Rosado
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario

// Tarjeta grande de un producto (catálogo y favoritos)
@Composable
fun ProductoCard(
    producto: Producto,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit,
    onVerDetalleClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borde)
    ) {
        ImagenProducto(
            categoria = producto.categoria,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Column(modifier = Modifier.padding(16.dp)) {

            // Chip de categoría y corazón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Etiqueta(texto = producto.categoria.texto)

                    if (producto.estado == EstadoProducto.AGOTADO) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Etiqueta(texto = EstadoProducto.AGOTADO.texto, colorFondo = Rosado, colorTexto = Naranjo)
                    }
                }

                BotonFavorito(
                    esFavorito = esFavorito,
                    onClick = onFavoritoClick
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = producto.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = producto.descripcion,
                fontSize = 14.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Material, precio y botón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = producto.material,
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                    Text(
                        text = formatearPrecio(producto.precioBase),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                }

                OutlinedButton(
                    onClick = onVerDetalleClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Ver detalle", color = TextoOscuro)
                }
            }
        }
    }
}
