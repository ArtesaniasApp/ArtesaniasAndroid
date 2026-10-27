package com.example.ArtesanApp.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.ArtesanApp.ui.theme.CelesteClaro
import com.example.ArtesanApp.ui.theme.CelesteOscuro
import com.example.ArtesanApp.ui.theme.TextoSecundario
import com.example.ArtesanApp.navigation.Routes

// Barra de abajo del cliente. "rutaActual" indica qué ítem se ve seleccionado.
@Composable
fun BarraInferior(
    navController: NavController,
    rutaActual: String
) {
    val colores = NavigationBarItemDefaults.colors(
        indicatorColor = CelesteClaro,
        selectedIconColor = CelesteOscuro,
        selectedTextColor = CelesteOscuro,
        unselectedIconColor = TextoSecundario,
        unselectedTextColor = TextoSecundario
    )

    NavigationBar(containerColor = Color.White) {

        NavigationBarItem(
            selected = rutaActual == Routes.CATALOGO,
            onClick = { navegarA(navController, Routes.CATALOGO) },
            icon = { Icon(Icons.Outlined.Storefront, contentDescription = null) },
            label = { Text("Catálogo") },
            colors = colores
        )

        NavigationBarItem(
            selected = rutaActual == Routes.FAVORITOS,
            onClick = { navegarA(navController, Routes.FAVORITOS) },
            icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) },
            label = { Text("Favoritos") },
            colors = colores
        )

        NavigationBarItem(
            selected = rutaActual == Routes.CARRITO,
            onClick = {
                navegarA(navController, Routes.CARRITO)
            },
            icon = {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = "Carrito"
                )
            },
            label = {
                Text("Carrito")
            },
            colors = colores
        )

        NavigationBarItem(
            selected = rutaActual == Routes.SOLICITUDES,
            onClick = { navegarA(navController, Routes.SOLICITUDES) },
            icon = { Icon(Icons.Outlined.Receipt, contentDescription = null) },
            label = { Text("Solicitudes") },
            colors = colores
        )
    }
}

// Navega sin apilar pantallas repetidas: vuelve hasta el catálogo y abre la ruta pedida
private fun navegarA(navController: NavController, ruta: String) {
    navController.navigate(ruta) {
        popUpTo(Routes.CATALOGO)
        launchSingleTop = true
    }
}
