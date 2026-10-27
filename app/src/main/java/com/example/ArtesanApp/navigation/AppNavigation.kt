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
import com.example.ArtesanApp.view.QrScreen
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

    // Por ahora la app tiene un solo usuario y es el administrador.
    // Cuando se conecte el backend, este valor saldrá del rol del usuario que inició sesión
    val esAdmin = true

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        // Pantalla de bienvenida
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onIngresarClick = {
                    navController.navigate(Routes.LOGIN)
                },
                onEncuentranosClick = {
                    navController.navigate(Routes.QR)
                }
            )
        }

        // Lector de códigos QR (se abre desde la bienvenida, no necesita iniciar sesión)
        composable(Routes.QR) {
            QrScreen(
                navController = navController
            )
        }

        // Pantalla de inicio de sesión
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLoginClick = { correo, contrasena ->
                    // Aquí conectaremos el inicio de sesión.
                    // Según el rol: CLIENTE -> "catalogo", ADMIN -> "inventario"
                    navController.navigate(Routes.CATALOGO) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla de registro de clientes (se abre desde el login)
        composable(Routes.REGISTRO) {
            RegistroScreen(
                navController = navController
            )
        }

        // ---------- Tienda del cliente ----------
        composable(Routes.CATALOGO) {
            CatalogoScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                favoritoViewModel = favoritoViewModel,
                carritoViewModel = carritoViewModel,
                esAdmin = esAdmin
            )
        }

        // {idProducto} es un parámetro de la ruta, ej: "detalle/3"
        composable("${Routes.DETALLE}/{idProducto}") { backStackEntry ->
            val idProducto = backStackEntry.arguments?.getString("idProducto")?.toLongOrNull() ?: 0

            DetalleProductoScreen(
                navController = navController,
                idProducto = idProducto,
                productoViewModel = productoViewModel,
                favoritoViewModel = favoritoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        composable(Routes.FAVORITOS) {
            FavoritosScreen(
                navController = navController,
                favoritoViewModel = favoritoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // ---------- Administración ----------

        composable(Routes.INVENTARIO) {
            InventarioScreen(
                navController = navController,
                productoViewModel = productoViewModel
            )
        }

        // idProducto = 0 -> crear producto nuevo; otro número -> editar ese producto
        composable("${Routes.PRODUCTO_FORM}/{idProducto}") { backStackEntry ->
            val idProducto = backStackEntry.arguments?.getString("idProducto")?.toLongOrNull() ?: 0

            ProductoFormScreen(
                navController = navController,
                idProducto = idProducto,
                productoViewModel = productoViewModel
            )
        }

        composable(Routes.CARRITO) {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel
            )
        }

        // Resumen de la compra, IVA y método de pago (pago simulado, no descuenta stock)
        composable(Routes.CHECKOUT) {
            CheckoutScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel,
                solicitudViewModel = solicitudViewModel
            )
        }

        composable(Routes.SOLICITUDES) {
            SolicitudesScreen(
                navController = navController,
                solicitudViewModel = solicitudViewModel
            )
        }

        // {numero} es el número de la solicitud, ej: "solicitud/2"
        composable("${Routes.SOLICITUD}/{numero}") { backStackEntry ->
            val numero = backStackEntry.arguments?.getString("numero")?.toIntOrNull() ?: 0

            DetalleSolicitudScreen(
                navController = navController,
                numeroSolicitud = numero,
                solicitudViewModel = solicitudViewModel
            )
        }
    }
}