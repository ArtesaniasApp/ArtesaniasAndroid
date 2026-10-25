package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.ProductoCard
import com.example.ArtesanApp.viewmodel.FavoritoViewModel
import kotlinx.coroutines.launch

// CU02: favoritos del usuario guardados en Room, del más reciente al más antiguo
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

            if (favoritos.isEmpty()) {
                item {
                    Text(
                        text = "Aún no has guardado piezas favoritas.",
                        color = TextoSecundario,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    )
                }
            } else {
                items(
                    items = favoritos,
                    key = { it.id }
                ) { producto ->

                    ProductoCard(
                        producto = producto,
                        esFavorito = true,
                        onFavoritoClick = {
                            favoritoViewModel.cambiarFavorito(
                                producto = producto,
                                esFavorito = true
                            )

                            alcance.launch {
                                snackbarHostState.showSnackbar(
                                    "Quitado de favoritos"
                                )
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