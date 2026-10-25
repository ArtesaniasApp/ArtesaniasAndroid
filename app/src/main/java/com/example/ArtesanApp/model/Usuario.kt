package com.example.ArtesanApp.model

data class Usuario(
    val idUsuario: Long = 0,
    val nombre: String,
    val email: String,
    val telefono: String? = null,
    val password: String,
    val tipoUsuario: TipoUsuario
)

