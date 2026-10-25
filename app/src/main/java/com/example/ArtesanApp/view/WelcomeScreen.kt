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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Fondo = Color(0xFFF5F6F8)
private val Celeste = Color(0xFF4BA0BF)
private val CelesteClaro = Color(0xFFDDF4FA)
private val Rosado = Color(0xFFF9D9E5)
private val TextoOscuro = Color(0xFF263D59)
private val TextoSecundario = Color(0xFF66758A)

@Composable
fun WelcomeScreen(
    onIngresarClick: () -> Unit = {}
) {
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

        // Tarjeta principal
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = 32.dp,
                        vertical = 36.dp
                    ),
                horizontalAlignment = Alignment.Start
            ) {
                // Icono decorativo
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Color(0xFFE6DDF4)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✧",
                        fontSize = 36.sp,
                        color = TextoOscuro
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Etiqueta de demostración
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(CelesteClaro)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                ) {
                    Text(
                        text = "Versión demo",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF327F9F)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Nombre de la aplicación
                Text(
                    text = "ArtesanApp",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF172D49)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Descripción
                Text(
                    text = "Tienda de artesanías hechas 100% a mano y con cariño.",
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Ingresar
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
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Aviso inferior
                Text(
                    text = "Acceso seguro de demostración." + " No se guardan credenciales.",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = TextoSecundario
                )
            }
        }
    }
}