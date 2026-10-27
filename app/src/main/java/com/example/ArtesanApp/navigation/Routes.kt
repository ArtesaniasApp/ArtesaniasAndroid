package com.example.ArtesanApp.navigation

// Nombres de todas las rutas de la app, escritos una sola vez.
// Las rutas con parámetro se arman así: "${Routes.DETALLE}/{idProducto}" en el NavHost
// y "${Routes.DETALLE}/${producto.id}" al navegar
object Routes {
    const val WELCOME = "welcome"
    const val QR = "qr"
    const val LOGIN = "login"
    const val REGISTRO = "registro"

    // Tienda del cliente
    const val CATALOGO = "catalogo"
    const val DETALLE = "detalle"
    const val FAVORITOS = "favoritos"
    const val CARRITO = "carrito"
    const val CHECKOUT = "checkout"
    const val SOLICITUDES = "solicitudes"
    const val SOLICITUD = "solicitud"

    // Administración
    const val INVENTARIO = "inventario"
    const val PRODUCTO_FORM = "producto_form"
}
