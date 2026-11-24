package com.example.ArtesanApp.dto

data class RegistroRequest(
    val nombre: String,
    val email: String,
    val telefono: String,
    val password: String,
    val direccion: String
)
