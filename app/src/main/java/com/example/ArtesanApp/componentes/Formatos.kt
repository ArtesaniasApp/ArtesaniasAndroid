package com.example.ArtesanApp.componentes

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatearPrecio(precio: Int): String {
    return "$" + String.format(Locale.forLanguageTag("es-CL"), "%,d", precio)
}

fun formatearFecha(fecha: String): String {
    return LocalDateTime.parse(fecha).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
}

fun formatearMetodoPago(nombre: String): String {
    return when (nombre) {
        "TARJETA_DEBITO" -> "Tarjeta de débito"
        "TARJETA_CREDITO" -> "Tarjeta de crédito"
        "TRANSFERENCIA" -> "Transferencia bancaria"
        else -> nombre
    }
}
