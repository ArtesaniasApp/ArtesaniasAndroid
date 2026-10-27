package com.example.ArtesanApp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.FiltroDesplegable
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import com.example.ArtesanApp.navigation.Routes

// CU06: el administrador gestiona el inventario (stock, estado, crear y editar productos)
@Composable
fun InventarioScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val productos by productoViewModel.productos.collectAsState()

    Scaffold(
        topBar = { BarraSuperior() },
        containerColor = Fondo
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Encabezado(
                    etiqueta = "Administración",
                    titulo = "Inventario",
                    subtitulo = "${productos.size} productos registrados"
                )
            }

            // Crear producto: id 0 significa "producto nuevo"
            item {
                Button(
                    onClick = {
                        navController.navigate("${Routes.PRODUCTO_FORM}/0")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Celeste,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Nuevo producto", fontWeight = FontWeight.Bold)
                }
            }

            items(
                items = productos,
                key = {
                    it.id
                }
            ) { producto ->

                InventarioItem(
                    producto = producto,
                    onRestarStock = {
                        productoViewModel.cambiarStock(producto, -1)
                    },
                    onSumarStock = {
                        productoViewModel.cambiarStock(producto, 1)
                    },
                    onCambiarEstado = { nuevoEstado ->
                        productoViewModel.cambiarEstado(producto, nuevoEstado)
                    },
                    onEditar = {
                        navController.navigate("${Routes.PRODUCTO_FORM}/${producto.id}")
                    }
                )
            }
        }
    }
}

// Tarjeta de un producto en el inventario
@Composable
fun InventarioItem(
    producto: Producto,
    onRestarStock: () -> Unit,
    onSumarStock: () -> Unit,
    onCambiarEstado: (EstadoProducto) -> Unit,
    onEditar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borde)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Nombre, categoría y precio + botón editar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Text(
                        text = "${producto.categoria.texto} ·${producto.material} · ${formatearPrecio(producto.precioBase)}",
                        fontSize = 13.sp,
                        color = TextoSecundario
                    )
                }

                TextButton(onClick = onEditar) {
                    Icon(Icons.Outlined.Edit, contentDescription = null, tint = TextoOscuro)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Editar", color = TextoOscuro)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stock con botones - y +, y estado con lista desplegable
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(
                    onClick = onRestarStock,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Filled.Remove, contentDescription = "Restar stock")
                }

                Text(
                    text = "${producto.stockDisponible}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                OutlinedIconButton(
                    onClick = onSumarStock,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Sumar stock")
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Muestra los textos de los estados y devuelve el estado elegido
                FiltroDesplegable(
                    opciones = EstadoProducto.entries.map { it.texto },
                    seleccionada = producto.estado.texto,
                    onSeleccionar = { texto ->
                        onCambiarEstado(EstadoProducto.entries.first { it.texto == texto })
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
