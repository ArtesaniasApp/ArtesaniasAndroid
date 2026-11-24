package com.example.ArtesanApp.model

data class Solicitud(
    val id: Long,
    val fecha: String,
    val estado: EstadoPedido,
    val metodoPago: String,
    val direccionDespacho: String,
    val total: Int,
    val clienteNombre: String,
    val clienteEmail: String,
    val detalles: List<DetalleSolicitud>
)
