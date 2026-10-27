package com.example.ArtesanApp.view

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.model.Categoria
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.componentes.BarraSuperior
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.componentes.FiltroDesplegable
import com.example.ArtesanApp.viewmodel.ProductoViewModel

// CU06: formulario para crear (idProducto = 0) o editar un producto
@Composable
fun ProductoFormScreen(
    navController: NavController,
    idProducto: Long,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val productos by productoViewModel.productos.collectAsState()

    // Si es edición, busca el producto. Si es nuevo, queda en null
    val productoEditar = productos.find { it.id == idProducto }

    // remember(productoEditar): cuando llega el producto desde Room, los campos se llenan con sus datos
    var nombre by remember(productoEditar) { mutableStateOf(productoEditar?.nombre ?: "") }
    var categoria by remember(productoEditar) { mutableStateOf(productoEditar?.categoria ?: Categoria.CERAMICA) }
    var material by remember(productoEditar) { mutableStateOf(productoEditar?.material ?: "") }
    var descripcion by remember(productoEditar) { mutableStateOf(productoEditar?.descripcion ?: "") }
    var descripcionMaterial by remember(productoEditar) { mutableStateOf(productoEditar?.descripcionMaterial ?: "") }
    var descripcionTecnica by remember(productoEditar) { mutableStateOf(productoEditar?.descripcionTecnica ?: "") }
    var precio by remember(productoEditar) { mutableStateOf(productoEditar?.precioBase?.toString() ?: "") }
    var stock by remember(productoEditar) { mutableStateOf(productoEditar?.stockDisponible?.toString() ?: "") }

    var mensajeError by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = { BarraSuperior() },
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
                Text(text = "Volver al inventario", color = TextoOscuro, fontWeight = FontWeight.Bold)
            }

            Encabezado(
                etiqueta = "Inventario",
                titulo = if (idProducto == 0L) "Nuevo producto" else "Editar producto"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(valor = nombre, onCambio = { nombre = it }, etiqueta = "Nombre")

            Text(text = "Categoría", color = TextoOscuro, fontWeight = FontWeight.Bold)
            FiltroDesplegable(
                opciones = Categoria.entries.map { it.texto },
                seleccionada = categoria.texto,
                onSeleccionar = { texto ->
                    categoria = Categoria.entries.first { it.texto == texto }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            CampoTexto(valor = material, onCambio = { material = it }, etiqueta = "Material (ej: Greda, Lana)")
            CampoTexto(valor = descripcion, onCambio = { descripcion = it }, etiqueta = "Descripción")
            CampoTexto(valor = descripcionMaterial, onCambio = { descripcionMaterial = it }, etiqueta = "Detalle del material")
            CampoTexto(valor = descripcionTecnica, onCambio = { descripcionTecnica = it }, etiqueta = "Detalle técnico (medidas, etc.)")
            CampoTexto(valor = precio, onCambio = { precio = it }, etiqueta = "Precio", soloNumeros = true)
            CampoTexto(valor = stock, onCambio = { stock = it }, etiqueta = "Stock disponible", soloNumeros = true)

            if (mensajeError.isNotBlank()) {
                Text(text = mensajeError, color = Color.Red, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    val precioNumero = precio.toIntOrNull()
                    val stockNumero = stock.toIntOrNull()

                    // Validaciones
                    if (nombre.isBlank() || material.isBlank() || descripcion.isBlank()) {
                        mensajeError = "Completa nombre, material y descripción."
                    } else if (precioNumero == null || precioNumero <= 0) {
                        mensajeError = "El precio debe ser un número mayor a 0."
                    } else if (stockNumero == null || stockNumero < 0) {
                        mensajeError = "El stock debe ser un número igual o mayor a 0."
                    } else {

                        val estadoActual = productoEditar?.estado ?: EstadoProducto.DISPONIBLE

                        val producto = Producto(
                            id = productoEditar?.id ?: 0,
                            categoria = categoria,
                            estado = productoViewModel.calcularEstado(estadoActual, stockNumero),
                            nombre = nombre,
                            descripcion = descripcion,
                            material = material,
                            descripcionMaterial = descripcionMaterial,
                            descripcionTecnica = descripcionTecnica,
                            precioBase = precioNumero,
                            stockDisponible = stockNumero
                        )

                        if (productoEditar == null) {
                            productoViewModel.insertar(producto)
                        } else {
                            productoViewModel.actualizar(producto)
                        }

                        navController.popBackStack()
                    }
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
                Text(text = "Guardar producto", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Campo de texto del formulario con su espacio debajo
@Composable
fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    soloNumeros: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = {
            Text(etiqueta)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (soloNumeros) KeyboardType.Number else KeyboardType.Text
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))
}
