package com.example.ArtesanApp.model

data class Solicitud(
    val numero: Int,
    val fecha: String,
    val estado: EstadoPedido,
    val metodoPago: MetodoPago,
    val total: Int,
    val detalles: List<DetalleSolicitud>
)
