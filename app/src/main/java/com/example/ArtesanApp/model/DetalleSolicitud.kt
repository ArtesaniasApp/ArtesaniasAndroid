package com.example.ArtesanApp.model

data class DetalleSolicitud(
    val productoId: Long,
    val productoNombre: String,
    val clasificacion: String,
    val cantidad: Int,
    val precioUnitario: Int,
    val subtotal: Int
)
