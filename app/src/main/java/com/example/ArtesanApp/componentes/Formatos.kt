package com.example.ArtesanApp.componentes

import java.util.Locale

fun formatearPrecio(precio: Int): String {
    return "$" + String.format(Locale.forLanguageTag("es-CL"), "%,d", precio)
}
