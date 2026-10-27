package com.example.ArtesanApp.model

// Categorías de artesanía. "texto" es lo que se muestra en pantalla.
// En la tabla producto, Room guarda el nombre del valor (ej: "CERAMICA")
enum class Categoria(val texto: String) {
    CERAMICA("Cerámica"),
    TEXTIL("Textil"),
    MADERA("Madera"),
    JOYERIA("Joyería"),
    CESTERIA("Cestería")
}
