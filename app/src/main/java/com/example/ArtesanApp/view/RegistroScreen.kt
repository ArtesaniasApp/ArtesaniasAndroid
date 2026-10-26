package com.example.ArtesanApp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ArtesanApp.componentes.Encabezado
import com.example.ArtesanApp.ui.theme.Celeste
import com.example.ArtesanApp.ui.theme.CelesteClaro
import com.example.ArtesanApp.ui.theme.Fondo
import com.example.ArtesanApp.ui.theme.Rosado
import com.example.ArtesanApp.ui.theme.TextoOscuro
import com.example.ArtesanApp.viewmodel.RegistroViewModel

// Pantalla de registro de clientes. Se abre desde el login y al terminar vuelve a él
@Composable
fun RegistroScreen(
    navController: NavController,
    registroViewModel: RegistroViewModel = viewModel()
) {
    // Texto del aviso que se muestra al apretar "Registrarme" ("" = no mostrar aviso)
    var mensaje by remember { mutableStateOf("") }
    var registroExitoso by remember { mutableStateOf(false) }

    // Fondo con los mismos círculos decorativos del login
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo),
        contentAlignment = Alignment.Center
    ) {
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

        // Tarjeta principal con el formulario
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 32.dp)
            ) {

                Encabezado(
                    etiqueta = "Nuevo cliente",
                    titulo = "Crea tu cuenta",
                    subtitulo = "Regístrate para guardar favoritos y armar tu carro."
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = registroViewModel.nombre,
                    onValueChange = { registroViewModel.cambiarNombre(it) },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = registroViewModel.email,
                    onValueChange = { registroViewModel.cambiarEmail(it) },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = registroViewModel.telefono,
                    onValueChange = { registroViewModel.cambiarTelefono(it) },
                    label = { Text("Teléfono (opcional)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = registroViewModel.direccion,
                    onValueChange = { registroViewModel.cambiarDireccion(it) },
                    label = { Text("Dirección de despacho") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = registroViewModel.password,
                    onValueChange = { registroViewModel.cambiarPassword(it) },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = registroViewModel.confirmarPassword,
                    onValueChange = { registroViewModel.cambiarConfirmarPassword(it) },
                    label = { Text("Confirmar contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val error = registroViewModel.validarRegistro()

                        if (error == "") {
                            registroExitoso = true
                            mensaje = "Tu cuenta fue creada con éxito. Ya puedes iniciar sesión."
                        } else {
                            registroExitoso = false
                            mensaje = error
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
                    Text(
                        text = "Registrarme",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Ya tengo cuenta, volver al login", color = TextoOscuro)
                }
            }
        }
    }

    // Aviso de éxito o de error
    if (mensaje != "") {
        AlertDialog(
            onDismissRequest = { mensaje = "" },
            title = {
                Text(if (registroExitoso) "¡Registro exitoso!" else "Error")
            },
            text = {
                Text(mensaje)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mensaje = ""
                        // Si salió bien, vuelve a la pantalla de login
                        if (registroExitoso) {
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
