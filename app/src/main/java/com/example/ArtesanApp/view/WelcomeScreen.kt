package com.example.ArtesanApp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ArtesanApp.componentes.Etiqueta
import com.example.ArtesanApp.componentes.ImagenProducto
import com.example.ArtesanApp.model.Categoria
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.CelesteClaro
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.Marino
import com.example.ArtesanApp.ui.theme.Rosado
import com.example.ArtesanApp.ui.theme.TextoSecundario
import kotlinx.coroutines.delay

// Portada de la app: versión y título arriba, carrusel de categorías al medio y botón Ingresar abajo
@Composable
fun WelcomeScreen(
    onIngresarClick: () -> Unit = {},
    onEncuentranosClick: () -> Unit = {}
) {
    // Una página del carrusel por cada categoría
    val categorias = Categoria.entries

    val estadoCarrusel = rememberPagerState(pageCount = { categorias.size })

    // Cada 3 segundos pasa a la siguiente categoría. Al llegar a la última vuelve a la primera
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val siguiente = (estadoCarrusel.currentPage + 1) % categorias.size
            estadoCarrusel.animateScrollToPage(siguiente)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo),
        contentAlignment = Alignment.Center
    ) {
        // Círculos decorativos del fondo
        Box(
            modifier = Modifier
                .offset(x = (-145).dp, y = (-310).dp)
                .size(300.dp)
                .background(CelesteClaro, CircleShape)
        )

        Box(
            modifier = Modifier
                .offset(x = 180.dp, y = 330.dp)
                .size(300.dp)
                .background(Rosado, CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- Arriba: versión y título ----------

            Etiqueta(texto = "Versión demo")

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "ArtesanApp",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                color = Marino
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Tienda de artesanías hechas 100% a mano, con cariño.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = TextoSecundario
            )

            // ---------- Medio: carrusel de categorías ----------

            // weight(1f): el carrusel ocupa todo el espacio libre entre el título y el botón
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = estadoCarrusel,
                    pageSpacing = 16.dp
                ) { pagina ->
                    TarjetaCarrusel(categoria = categorias[pagina])
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Puntos que indican en qué página va el carrusel
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in categorias.indices) {
                        Box(
                            modifier = Modifier
                                .size(if (i == estadoCarrusel.currentPage) 10.dp else 8.dp)
                                .background(
                                    if (i == estadoCarrusel.currentPage) Celeste else Borde,
                                    CircleShape
                                )
                        )
                    }
                }
            }

            // ---------- Abajo: botón Ingresar ----------

            Button(
                onClick = onIngresarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Celeste,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Ingresar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón que lleva al lector QR
            OutlinedButton(
                onClick = onEncuentranosClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, Celeste),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Celeste
                )
            ) {
                Icon(Icons.Filled.QrCodeScanner, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "¡Encuéntranos acá!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Acceso de demostración. No se guardan credenciales.",
                fontSize = 13.sp,
                color = TextoSecundario
            )
        }
    }
}

// Una página del carrusel: solo la imagen de la categoría (la misma de los productos)
@Composable
fun TarjetaCarrusel(categoria: Categoria) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Borde)
    ) {
        ImagenProducto(
            categoria = categoria,
            tamanoIcono = 96.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )
    }
}
