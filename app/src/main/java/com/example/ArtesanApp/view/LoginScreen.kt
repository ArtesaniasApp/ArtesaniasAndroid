package com.example.ArtesanApp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ArtesanApp.model.Usuario
import com.example.ArtesanApp.viewmodel.LoginViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.navigation.NavController
import com.example.ArtesanApp.navigation.Routes

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    onLoginExitoso: (Usuario) -> Unit = {}
) {
    val cargando by loginViewModel.cargando.collectAsState()
    val mensaje by loginViewModel.mensaje.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6F8)),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .offset(x = (-145).dp, y = (-310).dp)
                .size(300.dp)
                .background(
                    Color(0xFFDDF4FA),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .offset(x = 180.dp, y = 330.dp)
                .size(300.dp)
                .background(
                    Color(0xFFF9D9E5),
                    CircleShape
                )
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = 28.dp,
                        vertical = 32.dp
                    ),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "ArtesanApp",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF172D49)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "¡Qué bueno tenerte de vuelta!",
                    fontSize = 16.sp,
                    color = Color(0xFF66758A)
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Text(
                    text = "Iniciar sesión",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263D59)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                OutlinedTextField(
                    value = loginViewModel.email,
                    onValueChange = {loginViewModel.cambiarEmail(it)},
                    label = {
                        Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedTextField(
                    value = loginViewModel.password,
                    onValueChange = {
                        loginViewModel.cambiarPassword(it)
                    },
                    label = {
                        Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(
                    onClick = {
                        loginViewModel.iniciarSesion(onExito = onLoginExitoso)
                    },
                    enabled = !cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4BA0BF),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (cargando) "Ingresando..." else "Ingresar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (mensaje != "") {
                        AlertDialog(
                            onDismissRequest = {
                                loginViewModel.mensajeMostrado()
                            },
                            title = {
                                Text("Error")
                            },
                            text = {
                                Text(mensaje)
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        loginViewModel.mensajeMostrado()
                                    }
                                ) {
                                    Text("Aceptar")
                                }
                            }
                        )
                    }

                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {
                        navController.navigate(Routes.REGISTRO)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4BA0BF),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "¡Regístrate acá!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}