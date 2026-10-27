package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.ImagenProducto
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.viewmodel.CarritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import kotlinx.coroutines.flow.map

@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel(),
    productoViewModel: ProductoViewModel = viewModel()
) {
    val cantidades by carritoViewModel.carrito.collectAsState()
    val productos by productoViewModel.productos.collectAsState()

    val productosCarrito = productos.filter {
        cantidades.containsKey(it.id)
    }

    val total = productosCarrito.sumOf { producto ->
        producto.precioBase.toLong() *
                (cantidades[producto.id] ?: 0)
    }

    Scaffold(
        topBar = {
            BarraSuperior()
        },
        bottomBar = {
            BarraInferior(navController, "carrito")
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text(
                text = "Mi carrito",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))

            if (productosCarrito.isEmpty()) {
                Text("Tu carrito está vacío.")
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = productosCarrito,
                        key = { it.id }
                    ) { producto ->
                        val cantidad = cantidades[producto.id] ?: 0

                        CarritoItem(
                            producto = producto,
                            cantidad = cantidad,
                            onQuitar = {
                                carritoViewModel.quitar(producto.id)
                            },
                            onIncrementar = {
                                carritoViewModel.incrementar(producto)
                            },
                            onEliminar = {
                                carritoViewModel.eliminar(producto.id)
                            }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Total: $${total}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        navController.navigate("checkout")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar compra")
                }

                TextButton(
                    onClick = { carritoViewModel.vaciar() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Vaciar carrito")
                }
            }
        }
    }
}

@Composable
private fun CarritoItem(
    producto: Producto,
    cantidad: Int,
    onQuitar: () -> Unit,
    onIncrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImagenProducto(
            categoria = producto.categoria,
            modifier = Modifier
                .width(100.dp)
                .height(110.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Text("Precio unitario: $${producto.precioBase}")
            Text("Cantidad: $cantidad")
            Text(
                "Subtotal: $${producto.precioBase.toLong() * cantidad}"
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onQuitar) {
                    Text("−")
                }

                Spacer(Modifier.width(12.dp))

                Text(
                    text = "$cantidad",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(Modifier.width(12.dp))

                OutlinedButton(onClick = onIncrementar) {
                    Text("+")
                }
            }

            TextButton(onClick = onEliminar) {
                Text("Eliminar")
            }
        }
    }
}