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
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.viewmodel.CarritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch
import com.example.ArtesanApp.navigation.Routes

@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel(),
    productoViewModel: ProductoViewModel = viewModel(),
    esAdmin: Boolean = false
) {
    val cantidades by carritoViewModel.carrito.collectAsState()
    val productos by productoViewModel.productos.collectAsState()
    val cargando by productoViewModel.cargando.collectAsState()
    val mensajeServidor by productoViewModel.mensaje.collectAsState()

    val productosCarrito = productos.filter {
        cantidades.containsKey(it.id)
    }

    val total = productosCarrito.sumOf { producto ->
        producto.precioBase * (cantidades[producto.id] ?: 0)
    }

    val hayProblemas = productosCarrito.any { producto ->
        carritoViewModel.revisarProducto(producto, cantidades[producto.id] ?: 0) != ""
    }

    val noDisponibles = if (productos.isEmpty()) {
        emptyList()
    } else {
        carritoViewModel.productosNoDisponibles(productos)
    }

    val puedeContinuar = !cargando && !hayProblemas &&
            noDisponibles.isEmpty() && productosCarrito.isNotEmpty()

    val snackbarHostState = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        productoViewModel.cargarProductos(esAdmin)
    }

    LaunchedEffect(mensajeServidor) {
        if (mensajeServidor != "") {
            snackbarHostState.showSnackbar(mensajeServidor)
            productoViewModel.mensajeMostrado()
        }
    }

    Scaffold(
        topBar = {
            BarraSuperior()
        },
        bottomBar = {
            BarraInferior(navController, Routes.CARRITO)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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

            if (cantidades.isEmpty()) {
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
                            aviso = carritoViewModel.revisarProducto(producto, cantidad),
                            onQuitar = {
                                carritoViewModel.quitar(producto.id)
                            },
                            onIncrementar = {
                                val agregado = carritoViewModel.incrementar(producto)

                                if (!agregado) {
                                    alcance.launch {
                                        snackbarHostState.showSnackbar(
                                            "Stock máximo alcanzado (${producto.stockDisponible} unidades)"
                                        )
                                    }
                                }
                            },
                            onEliminar = {
                                carritoViewModel.eliminar(producto.id)
                            }
                        )
                    }
                }

                if (noDisponibles.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Productos que ya no están disponibles: ${noDisponibles.size}",
                        color = Color.Red,
                        fontSize = 14.sp
                    )

                    TextButton(
                        onClick = {
                            noDisponibles.forEach { productoId ->
                                carritoViewModel.eliminar(productoId)
                            }
                        }
                    ) {
                        Text("Quitar no disponibles")
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Total: ${formatearPrecio(total)}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        navController.navigate(Routes.CHECKOUT)
                    },
                    enabled = puedeContinuar,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar compra")
                }

                if (hayProblemas || noDisponibles.isNotEmpty()) {
                    Text(
                        text = "Corrige los productos con aviso para continuar.",
                        color = Color.Red,
                        fontSize = 13.sp
                    )
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
    aviso: String,
    onQuitar: () -> Unit,
    onIncrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImagenProducto(
            categoria = producto.clasificacion,
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

            Text("Precio unitario: ${formatearPrecio(producto.precioBase)}")
            Text("Cantidad: $cantidad")
            Text(
                "Subtotal: ${formatearPrecio(producto.precioBase * cantidad)}"
            )

            if (aviso != "") {
                Text(text = aviso, color = Color.Red, fontSize = 13.sp)
            }

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