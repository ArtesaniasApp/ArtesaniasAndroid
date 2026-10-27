package com.example.ArtesanApp.componentes

import java.util.Locale

// 15900 -> "$15.900" (formato chileno: punto como separador de miles)
fun formatearPrecio(precio: Int): String {
    return "$" + String.format(Locale.forLanguageTag("es-CL"), "%,d", precio)
}
