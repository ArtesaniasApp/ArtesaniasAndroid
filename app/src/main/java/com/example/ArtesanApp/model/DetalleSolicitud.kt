package com.example.ArtesanApp.model

// Una línea de la solicitud: qué producto, cuántas unidades y a qué precio.
// Se copia el nombre y el precio para que la solicitud no cambie si después se edita el producto
data class DetalleSolicitud(
    val nombreProducto: String,
    val categoria: Categoria,       // para dibujar la imagen del producto
    val cantidad: Int,
    val precioUnitario: Int
)
