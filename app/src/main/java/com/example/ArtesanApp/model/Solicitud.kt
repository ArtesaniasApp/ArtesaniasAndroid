package com.example.ArtesanApp.model

// Solicitud de compra creada en el checkout. Por ahora vive solo en memoria (no es @Entity de Room)
data class Solicitud(
    val numero: Int,
    val fecha: String,
    val estado: EstadoPedido,
    val metodoPago: MetodoPago,
    val total: Int,
    val detalles: List<DetalleSolicitud>
)
