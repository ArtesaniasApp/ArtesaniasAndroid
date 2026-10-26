package com.example.ArtesanApp.view

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.FilaMonto
import com.example.ArtesanApp.componentes.TarjetaConTitulo
import com.example.ArtesanApp.componentes.TotalRemarcado
import com.example.ArtesanApp.componentes.formatearPrecio
import com.example.ArtesanApp.model.DetalleSolicitud
import com.example.ArtesanApp.model.MetodoPago
import com.example.ArtesanApp.ui.theme.Borde
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.viewmodel.CarritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import com.example.ArtesanApp.viewmodel.SolicitudViewModel
import kotlin.math.roundToInt

// Checkout: resumen del carro con subtotal, IVA y total, y elección del método de pago.
// El pago es simulado: al confirmar se crea una solicitud en memoria y NO se descuenta stock en Room
@Composable
fun CheckoutScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel(),
    productoViewModel: ProductoViewModel = viewModel(),
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    val cantidades by carritoViewModel.carrito.collectAsState()
    val productos by productoViewModel.productos.collectAsState()

    // Método elegido (null = todavía no elige ninguno)
    var metodoElegido by remember { mutableStateOf<MetodoPago?>(null) }

    var mostrarConfirmacion by remember { mutableStateOf(false) }

    // Número de la solicitud recién creada (0 = todavía no se confirma la compra)
    var numeroSolicitud by remember { mutableStateOf(0) }

    val productosCarrito = productos.filter { cantidades.containsKey(it.id) }

    // Los precios ya incluyen IVA (como en el comercio chileno):
    // total = suma de precios; subtotal (neto) = total / 1,19; IVA = total - subtotal
    val total = productosCarrito.sumOf { producto ->
        producto.precioBase * (cantidades[producto.id] ?: 0)
    }
    val subtotal = (total / 1.19).roundToInt()
    val iva = total - subtotal

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController, "carrito") },
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
                Text(text = "Volver al carro", color = TextoOscuro, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Encabezado(
                etiqueta = "Checkout",
                titulo = "Confirma tu compra",
                subtitulo = "Revisa el resumen y elige cómo quieres pagar."
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (productosCarrito.isEmpty()) {
                Text(text = "Tu carro está vacío.", color = TextoSecundario)
                return@Column
            }

            // ---------- Resumen de la compra ----------

            TarjetaConTitulo(titulo = "Resumen") {

                for (producto in productosCarrito) {
                    val cantidad = cantidades[producto.id] ?: 0

                    FilaMonto(
                        texto = "${producto.nombre} × $cantidad",
                        monto = formatearPrecio(producto.precioBase * cantidad)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 8.dp))

                FilaMonto(texto = "Subtotal (neto)", monto = formatearPrecio(subtotal))

                Spacer(modifier = Modifier.height(8.dp))

                FilaMonto(texto = "IVA (19%)", monto = formatearPrecio(iva))

                Spacer(modifier = Modifier.height(16.dp))

                TotalRemarcado(total = total)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- Método de pago ----------

            TarjetaConTitulo(titulo = "Método de pago") {

                for (metodo in MetodoPago.entries) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { metodoElegido = metodo },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = metodoElegido == metodo,
                            onClick = { metodoElegido = metodo },
                            colors = RadioButtonDefaults.colors(selectedColor = Celeste)
                        )
                        Text(text = metodo.texto, color = TextoOscuro)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { mostrarConfirmacion = true },
                enabled = metodoElegido != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Celeste,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Pagar ${formatearPrecio(total)}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            if (metodoElegido == null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Elige un método de pago para continuar.", fontSize = 13.sp, color = TextoSecundario)
            }
        }
    }

    // Pop up 1: pide confirmar la compra
    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Confirmar compra") },
            text = {
                Text("Vas a pagar ${formatearPrecio(total)} con ${metodoElegido?.texto}. ¿Confirmas la compra?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Arma las líneas de la solicitud recorriendo el carro en el orden en que se agregaron
                        val detalles = mutableListOf<DetalleSolicitud>()
                        for ((idProducto, cantidad) in cantidades) {
                            val producto = productos.find { it.id == idProducto }
                            if (producto != null) {
                                detalles.add(
                                    DetalleSolicitud(
                                        nombreProducto = producto.nombre,
                                        categoria = producto.categoria,
                                        cantidad = cantidad,
                                        precioUnitario = producto.precioBase
                                    )
                                )
                            }
                        }

                        // Pago simulado: se guarda la solicitud en memoria, no se descuenta stock en Room
                        numeroSolicitud = solicitudViewModel.agregarSolicitud(detalles, total, metodoElegido!!)
                        mostrarConfirmacion = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Pop up 2: solicitud creada. Al aceptar vacía el carro y abre "Mis solicitudes"
    if (numeroSolicitud != 0) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("¡Solicitud enviada!") },
            text = { Text("Tu solicitud N° $numeroSolicitud quedó registrada con estado Pendiente.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        numeroSolicitud = 0
                        carritoViewModel.vaciar()
                        navController.navigate("solicitudes") {
                            popUpTo("catalogo")
                        }
                    }
                ) {
                    Text("Ver mis solicitudes")
                }
            }
        )
    }
}
