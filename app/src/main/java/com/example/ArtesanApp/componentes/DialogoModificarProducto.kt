package com.example.ArtesanApp.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.ui.theme.TextoOscuro

// Pop up del administrador para cambiar el stock y el estado de un producto.
// Los cambios se guardan solo al presionar "Guardar"
@Composable
fun DialogoModificarProducto(
    producto: Producto,
    onCancelar: () -> Unit,
    onGuardar: (stock: Int, estado: EstadoProducto) -> Unit
) {
    // Copias locales: si el admin cancela, el producto queda igual
    var stock by remember {
        mutableStateOf(producto.stockDisponible)
    }

    var estado by remember {
        mutableStateOf(producto.estado)
    }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Modificar ${producto.nombre}") },
        text = {
            Column {
                Text(text = "Stock disponible", color = TextoOscuro, fontWeight = FontWeight.Bold)

                // Stock con botones - y +. Nunca baja de 0
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedIconButton(
                        onClick = {
                            if (stock > 0) {
                                stock = stock - 1
                            }
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Filled.Remove, contentDescription = "Restar stock")
                    }

                    Text(
                        text = "$stock",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    OutlinedIconButton(
                        onClick = { stock = stock + 1 },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Sumar stock")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Estado", color = TextoOscuro, fontWeight = FontWeight.Bold)

                // Muestra los textos de los estados y guarda el estado elegido
                FiltroDesplegable(
                    opciones = EstadoProducto.entries.map { it.texto },
                    seleccionada = estado.texto,
                    onSeleccionar = { texto ->
                        estado = EstadoProducto.entries.first { it.texto == texto }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onGuardar(stock, estado) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}
