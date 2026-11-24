package com.example.ArtesanApp.model

data class Usuario(
    val id: Long,
    val nombre: String,
    val email: String,
    val telefono: String?,
    val direccion: String?,
    val rol: String
)
