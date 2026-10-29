package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.model.Categoria
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.OrdenCatalogo
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.FiltroDesplegable
import com.example.ArtesanApp.componentes.ProductoCard
import com.example.ArtesanApp.viewmodel.FavoritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import com.example.ArtesanApp.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch
import com.example.ArtesanApp.navigation.Routes

@Composable
fun CatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel = viewModel(),
    favoritoViewModel: FavoritoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel = viewModel(),
    esAdmin: Boolean = false
) {
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    var categoria by remember {
        mutableStateOf<Categoria?>(null)
    }

    var material by remember {
        mutableStateOf("Todos")
    }

    var orden by remember {
        mutableStateOf(OrdenCatalogo.DESTACADOS)
    }

    val productos by productoViewModel.productos.collectAsState()
    val favoritos by favoritoViewModel.favoritos.collectAsState()

    val materiales = listOf("Todos") + productos.map { it.material }.distinct().sorted()

    val productosFiltrados = productoViewModel.filtrarCatalogo(
        productos = productos,
        texto = textoBusqueda,
        categoria = categoria,
        material = material,
        orden = orden,
        esAdmin = esAdmin
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, Routes.CATALOGO) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                    etiqueta = "Catálogo",
                    titulo = "Hecho a mano, cerca de ti"
                )
            }

            if (esAdmin) {
                item {
                    OutlinedButton(
                        onClick = {
                            navController.navigate(Routes.INVENTARIO)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Outlined.Inventory2, contentDescription = null, tint = TextoOscuro)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Inventario", color = TextoOscuro)
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = textoBusqueda,
                    onValueChange = {
                        textoBusqueda = it
                    },
                    placeholder = {
                        Text("Buscar artesanías...")
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = null)
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    FiltroDesplegable(
                        opciones = listOf("Todas") + Categoria.entries.map { it.texto },
                        seleccionada = categoria?.texto ?: "Todas",
                        onSeleccionar = { texto ->
                            categoria = Categoria.entries.find { it.texto == texto }
                        },
                        modifier = Modifier.weight(1f)
                    )

                    FiltroDesplegable(
                        opciones = materiales,
                        seleccionada = material,
                        onSeleccionar = { material = it },
                        modifier = Modifier.weight(1f)
                    )

                    FiltroDesplegable(
                        opciones = OrdenCatalogo.entries.map { it.texto },
                        seleccionada = orden.texto,
                        onSeleccionar = { texto ->
                            orden = OrdenCatalogo.entries.first { it.texto == texto }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (productos.isNotEmpty() && productosFiltrados.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "No encontramos artesanías con esos filtros.",
                        color = TextoSecundario
                    )
                }
            }

            items(
                items = productosFiltrados,
                key = { it.id }
            ) { producto ->

                val esFavorito = favoritos.any {
                    it.id == producto.id
                }

                ProductoCard(
                    producto = producto,
                    esFavorito = esFavorito,
                    onFavoritoClick = {
                        favoritoViewModel.cambiarFavorito(
                            producto,
                            esFavorito
                        )

                        val mensaje = if (esFavorito) {
                            "Quitado de favoritos"
                        } else {
                            "Añadido a favoritos"
                        }

                        alcance.launch {
                            snackbarHostState.showSnackbar(mensaje)
                        }
                    },
                    onVerDetalleClick = {
                        navController.navigate("${Routes.DETALLE}/${producto.id}")
                    },
                    onCarritoClick = {
                        if (producto.estado == EstadoProducto.INACTIVO) {
                            alcance.launch {
                                snackbarHostState.showSnackbar("Producto inactivo: no se puede agregar")
                            }
                        } else {
                            val agregado = carritoViewModel.agregar(producto)

                            alcance.launch {
                                val mensaje = if (agregado) {
                                    "Añadido al carrito"
                                } else {
                                    "Stock máximo alcanzado (${producto.stockDisponible} unidades)"
                                }

                                snackbarHostState.showSnackbar(mensaje)
                            }
                        }
                    }
                )
            }
        }
    }
}