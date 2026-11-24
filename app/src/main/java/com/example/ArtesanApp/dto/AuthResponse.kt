package com.example.ArtesanApp.dto

import com.example.ArtesanApp.model.Usuario

data class AuthResponse(
    val token: String,
    val usuario: Usuario
)
