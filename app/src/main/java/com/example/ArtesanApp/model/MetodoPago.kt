package com.example.ArtesanApp.model

// Métodos de pago del checkout (pago simulado). "texto" es lo que se muestra en pantalla
enum class MetodoPago(val texto: String) {
    DEBITO("Tarjeta de débito"),
    CREDITO("Tarjeta de crédito"),
    TRANSFERENCIA("Transferencia bancaria")
}
