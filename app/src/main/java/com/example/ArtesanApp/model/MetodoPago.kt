package com.example.ArtesanApp.model

enum class MetodoPago(val texto: String) {
    DEBITO("Tarjeta de débito"),
    CREDITO("Tarjeta de crédito"),
    TRANSFERENCIA("Transferencia bancaria")
}
