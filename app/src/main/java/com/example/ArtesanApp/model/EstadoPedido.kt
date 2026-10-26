package com.example.ArtesanApp.model

// Estados de una solicitud de compra (iguales a los del backend). "texto" es lo que se muestra en pantalla
enum class EstadoPedido(val texto: String) {
    PENDIENTE("Pendiente"),
    RECIBIDA("Recibida"),
    EN_PREPARACION("En preparación"),
    DESPACHADA("Despachada"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada")
}
