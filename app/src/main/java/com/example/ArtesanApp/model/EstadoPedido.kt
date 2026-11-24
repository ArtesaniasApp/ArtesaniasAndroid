package com.example.ArtesanApp.model

enum class EstadoPedido(val texto: String) {
    PENDIENTE("Pendiente"),
    RECIBIDA("Recibida"),
    EN_PREPARACION("En preparación"),
    DESPACHADA("Despachada"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada")
}
