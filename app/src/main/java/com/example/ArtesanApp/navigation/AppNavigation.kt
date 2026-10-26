package com.example.ArtesanApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ArtesanApp.view.CarritoScreen
import com.example.ArtesanApp.view.CatalogoScreen
import com.example.ArtesanApp.view.CheckoutScreen
import com.example.ArtesanApp.view.DetalleProductoScreen
import com.example.ArtesanApp.view.DetalleSolicitudScreen
import com.example.ArtesanApp.view.FavoritosScreen
import com.example.ArtesanApp.view.InventarioScreen
import com.example.ArtesanApp.view.LoginScreen
import com.example.ArtesanApp.view.ProductoFormScreen
import com.example.ArtesanApp.view.RegistroScreen
import com.example.ArtesanApp.view.SolicitudesScreen
import com.example.ArtesanApp.view.WelcomeScreen
import com.example.ArtesanApp.viewmodel.CarritoViewModel
import com.example.ArtesanApp.viewmodel.FavoritoViewModel
import com.example.ArtesanApp.viewmodel.ProductoViewModel
import com.example.ArtesanApp.viewmodel.SolicitudViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    // ViewModels de productos y favoritos creados una sola vez y compartidos por todas las pantallas.
    // Así, al guardar y volver atrás, la operación en Room no se cancela.
    val productoViewModel: ProductoViewModel = viewModel()
    val favoritoViewModel: FavoritoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val solicitudViewModel: SolicitudViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        // Pantalla de bienvenida
        composable("welcome") {
            WelcomeScreen(
                onIngresarClick = {
                    navController.navigate("login")
                }
            )
        }

        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginClick = { correo, contrasena ->
                    // Aquí conectaremos el inicio de sesión.
                    // Según el rol: CLIENTE -> "catalogo", ADMIN -> "inventario"
                    navController.navigate("catalogo") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de registro de clientes (se abre desde el login)
        composable("registro") {
            RegistroScreen(
                navController = navController
            )
        }

        // ---------- Tienda del cliente ----------
        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                favoritoViewModel = favoritoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // {idProducto} es un parámetro de la ruta, ej: "detalle/3"
        composable("detalle/{idProducto}") { backStackEntry ->
            val idProducto = backStackEntry.arguments?.getString("idProducto")?.toLongOrNull() ?: 0

            DetalleProductoScreen(
                navController = navController,
                idProducto = idProducto,
                productoViewModel = productoViewModel,
                favoritoViewModel = favoritoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                favoritoViewModel = favoritoViewModel
            )
        }

        // ---------- Administración ----------

        composable("inventario") {
            InventarioScreen(
                navController = navController,
                productoViewModel = productoViewModel
            )
        }

        // idProducto = 0 -> crear producto nuevo; otro número -> editar ese producto
        composable("producto_form/{idProducto}") { backStackEntry ->
            val idProducto = backStackEntry.arguments?.getString("idProducto")?.toLongOrNull() ?: 0

            ProductoFormScreen(
                navController = navController,
                idProducto = idProducto,
                productoViewModel = productoViewModel
            )
        }

        composable("carrito") {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel
            )
        }

        // Resumen de la compra, IVA y método de pago (pago simulado, no descuenta stock)
        composable("checkout") {
            CheckoutScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel,
                solicitudViewModel = solicitudViewModel
            )
        }

        composable("solicitudes") {
            SolicitudesScreen(
                navController = navController,
                solicitudViewModel = solicitudViewModel
            )
        }

        // {numero} es el número de la solicitud, ej: "solicitud/2"
        composable("solicitud/{numero}") { backStackEntry ->
            val numero = backStackEntry.arguments?.getString("numero")?.toIntOrNull() ?: 0

            DetalleSolicitudScreen(
                navController = navController,
                numeroSolicitud = numero,
                solicitudViewModel = solicitudViewModel
            )
        }
    }
}