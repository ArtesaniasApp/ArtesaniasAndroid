package com.example.ArtesanApp.dto

data class ErrorResponse(
    val estado: Int,
    val mensaje: String?,
    val errores: Map<String, String>?
)
