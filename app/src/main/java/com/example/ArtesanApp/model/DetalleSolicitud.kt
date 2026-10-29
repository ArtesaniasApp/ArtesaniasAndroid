package com.example.ArtesanApp.model

data class DetalleSolicitud(
    val nombreProducto: String,
    val categoria: Categoria,
    val cantidad: Int,
    val precioUnitario: Int
)
