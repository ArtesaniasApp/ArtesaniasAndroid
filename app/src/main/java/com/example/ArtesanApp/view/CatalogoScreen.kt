package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
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
import com.example.ArtesanApp.model.OrdenCatalogo
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.FiltroDesplegable
import com.example.ArtesanApp.componentes.ProductoCard
import com.example.ArtesanApp.viewmodel.FavoritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

// CU01: el cliente explora el catálogo con buscador y 3 filtros
@Composable
fun CatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel = viewModel(),
    favoritoViewModel: FavoritoViewModel = viewModel()
) {
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    // null significa "Todas" las categorías
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

    // Los materiales del filtro salen de los productos guardados (sin repetir)
    val materiales = listOf("Todos") + productos.map { it.material }.distinct().sorted()

    val productosFiltrados = productoViewModel.filtrarCatalogo(
        productos = productos,
        texto = textoBusqueda,
        categoria = categoria,
        material = material,
        orden = orden
    )

    // Aviso oscuro de abajo ("Añadido a favoritos")
    val snackbarHostState = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, "catalogo") },
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

            // Buscador
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

            // Filtros: categoría, material y orden
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    // El filtro muestra textos; al elegir uno se busca la categoría con ese texto.
                    // "Todas" no existe en el enum, por eso find devuelve null
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

            // productos.isNotEmpty(): no mostrar el aviso mientras Room todavía está cargando
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
                key = {
                    it.id
                }
            ) { producto ->

                val esFavorito = favoritos.any { it.id == producto.id }

                ProductoCard(
                    producto = producto,
                    esFavorito = esFavorito,
                    onFavoritoClick = {
                        favoritoViewModel.cambiarFavorito(producto, esFavorito)

                        val mensaje = if (esFavorito) "Quitado de favoritos" else "Añadido a favoritos"
                        alcance.launch {
                            snackbarHostState.showSnackbar(mensaje)
                        }
                    },
                    onVerDetalleClick = {
                        navController.navigate("detalle/${producto.id}")
                    }
                )
            }
        }
    }
}
