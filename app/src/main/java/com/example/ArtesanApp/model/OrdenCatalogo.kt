package com.example.ArtesanApp.model

// Formas de ordenar el catálogo. "texto" es lo que se muestra en el filtro
enum class OrdenCatalogo(val texto: String) {
    DESTACADOS("Destacados"),
    PRECIO_MENOR_A_MAYOR("Precio: menor a mayor"),
    PRECIO_MAYOR_A_MENOR("Precio: mayor a menor"),
    MAYOR_DISPONIBILIDAD("Mayor disponibilidad")
}
