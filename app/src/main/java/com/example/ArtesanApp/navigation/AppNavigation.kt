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
    val productoViewModel: ProductoViewModel = viewModel()
    val favoritoViewModel: FavoritoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val solicitudViewModel: SolicitudViewModel = viewModel()

    val esAdmin = true

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
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

        composable(Routes.QR) {
            QrScreen(
                navController = navController
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLoginClick = { correo, contrasena ->
                    navController.navigate(Routes.CATALOGO) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTRO) {
            RegistroScreen(
                navController = navController
            )
        }

        composable(Routes.CATALOGO) {
            CatalogoScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                favoritoViewModel = favoritoViewModel,
                carritoViewModel = carritoViewModel,
                esAdmin = esAdmin
            )
        }

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

        composable(Routes.INVENTARIO) {
            InventarioScreen(
                navController = navController,
                productoViewModel = productoViewModel
            )
        }

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