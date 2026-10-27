package com.example.ArtesanApp.model

// Estados de un producto. "texto" es lo que se muestra en pantalla.
// En la tabla producto, Room guarda el nombre del valor (ej: "DISPONIBLE")
enum class EstadoProducto(val texto: String) {
    DISPONIBLE("Disponible"),
    AGOTADO("Agotado"),
    INACTIVO("Inactivo")
}
