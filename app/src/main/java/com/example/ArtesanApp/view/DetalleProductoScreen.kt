package com.example.ArtesanApp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.Marino
import com.example.ArtesanApp.ui.theme.Naranjo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.componentes.BarraInferior
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.BotonFavorito
import com.example.ArtesanApp.componentes.Etiqueta
import com.example.ArtesanApp.componentes.ImagenProducto
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.viewmodel.FavoritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

// CU01: detalle de un producto. "onAgregarAlCarro" lo conecta el carro de compras
@Composable
fun DetalleProductoScreen(
    navController: NavController,
    idProducto: Long,
    productoViewModel: ProductoViewModel = viewModel(),
    favoritoViewModel: FavoritoViewModel = viewModel(),
    onAgregarAlCarro: (Producto, Int) -> Unit = { _, _ -> }
) {
    var cantidadTexto by remember {
        mutableStateOf("1")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    val productos by productoViewModel.productos.collectAsState()
    val favoritos by favoritoViewModel.favoritos.collectAsState()

    // Busca el producto por su id dentro de la lista guardada en Room
    val producto = productos.find { it.id == idProducto }

    val snackbarHostState = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, "catalogo") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Fondo
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            TextButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextoOscuro)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Volver al catálogo", color = TextoOscuro, fontWeight = FontWeight.Bold)
            }

            // Mientras Room entrega la lista, el producto todavía es null.
            // "return@Column" deja de dibujar el resto de la columna
            if (producto == null) {
                Text(text = "Cargando producto...", color = TextoSecundario)
                return@Column
            }

            val esFavorito = favoritos.any { it.id == producto.id }
            val hayStock = producto.estado == EstadoProducto.DISPONIBLE && producto.stockDisponible > 0

            Spacer(modifier = Modifier.height(8.dp))

            ImagenProducto(
                categoria = producto.categoria,
                tamanoIcono = 90.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Etiqueta(texto = producto.categoria.texto)

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre y corazón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto.nombre,
                    fontSize = 28.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Marino,
                    modifier = Modifier.weight(1f)
                )

                BotonFavorito(
                    esFavorito = esFavorito,
                    onClick = {
                        favoritoViewModel.cambiarFavorito(producto, esFavorito)

                        val mensaje = if (esFavorito) "Quitado de favoritos" else "Añadido a favoritos"
                        alcance.launch {
                            snackbarHostState.showSnackbar(mensaje)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = producto.descripcion, fontSize = 16.sp, color = TextoSecundario)

            Spacer(modifier = Modifier.height(16.dp))

            // Mini tarjetas: material y disponibilidad
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                MiniTarjeta(
                    titulo = "Material",
                    valor = producto.material,
                    colorValor = TextoOscuro,
                    modifier = Modifier.weight(1f)
                )

                MiniTarjeta(
                    titulo = "Disponibilidad",
                    valor = if (hayStock) "${producto.stockDisponible} unidades" else "Agotado",
                    colorValor = Naranjo,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Sobre el material", fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(text = producto.descripcionMaterial, color = TextoSecundario)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Detalle técnico", fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(text = producto.descripcionTecnica, color = TextoSecundario)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = formatearPrecio(producto.precioBase),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cantidad (solo números)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Cantidad", fontWeight = FontWeight.Bold, color = TextoOscuro)

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    value = cantidadTexto,
                    onValueChange = {
                        cantidadTexto = it
                        mensajeError = ""
                    },
                    singleLine = true,
                    enabled = hayStock,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(90.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            if (mensajeError.isNotBlank()) {
                Text(text = mensajeError, color = Color.Red, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val cantidad = cantidadTexto.toIntOrNull()

                    if (cantidad == null || cantidad < 1) {
                        mensajeError = "Ingresa una cantidad válida."
                    } else if (cantidad > producto.stockDisponible) {
                        mensajeError = "Solo quedan ${producto.stockDisponible} unidades."
                    } else {
                        onAgregarAlCarro(producto, cantidad)
                        alcance.launch {
                            snackbarHostState.showSnackbar("Producto agregado al carro")
                        }
                    }
                },
                enabled = hayStock,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Celeste,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Outlined.ShoppingBag, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Agregar al carro de cotización", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Tarjetita blanca con un título chico y un valor (Material / Disponibilidad)
@Composable
fun MiniTarjeta(
    titulo: String,
    valor: String,
    colorValor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borde)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = titulo, fontSize = 12.sp, color = TextoSecundario)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = valor, fontWeight = FontWeight.Bold, color = colorValor)
        }
    }
}
