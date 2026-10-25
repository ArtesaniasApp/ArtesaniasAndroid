package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.model.listaCategorias
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.view.componentes.BarraInferior
import com.example.ArtesanApp.view.componentes.BarraSuperior
import com.example.ArtesanApp.view.componentes.Encabezado
import com.example.ArtesanApp.view.componentes.ProductoCard
import com.example.ArtesanApp.viewmodel.FavoritoViewModel
import kotlinx.coroutines.launch

// CU02: favoritos del usuario guardados en Room, agrupados por categoría
@Composable
fun FavoritosScreen(
    navController: NavController,
    favoritoViewModel: FavoritoViewModel = viewModel()
) {
    val favoritos by favoritoViewModel.favoritos.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, "favoritos") },
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
                    etiqueta = "Selección personal",
                    titulo = "Mis favoritos",
                    subtitulo = "Guarda piezas para compararlas y cotizarlas cuando quieras."
                )
            }

            // Una sección por cada categoría
            for (categoria in listaCategorias) {

                val favoritosCategoria = favoritos.filter { it.categoria == categoria }

                item {
                    TituloCategoria(
                        categoria = categoria,
                        cantidad = favoritosCategoria.size
                    )
                }

                if (favoritosCategoria.isEmpty()) {
                    item {
                        CategoriaVacia()
                    }
                }

                items(
                    items = favoritosCategoria,
                    key = {
                        it.id
                    }
                ) { producto ->

                    ProductoCard(
                        producto = producto,
                        esFavorito = true,
                        onFavoritoClick = {
                            favoritoViewModel.cambiarFavorito(producto, true)
                            alcance.launch {
                                snackbarHostState.showSnackbar("Quitado de favoritos")
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
}

// "Cerámica .......... 1 guardado"
@Composable
fun TituloCategoria(
    categoria: String,
    cantidad: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = categoria,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextoOscuro
        )

        Text(
            text = if (cantidad == 1) "1 guardado" else "$cantidad guardados",
            fontSize = 13.sp,
            color = TextoSecundario
        )
    }
}

// Recuadro con borde punteado cuando la categoría no tiene favoritos
@Composable
fun CategoriaVacia() {
    Text(
        text = "Aún no has guardado piezas de esta categoría.",
        color = TextoSecundario,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    color = Borde,
                    cornerRadius = CornerRadius(20.dp.toPx()),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f))
                    )
                )
            }
            .padding(20.dp)
    )
}
