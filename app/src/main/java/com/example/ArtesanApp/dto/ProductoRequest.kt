package com.example.ArtesanApp.dto

data class ProductoRequest(
    val clasificacionId: Long,
    val nombre: String,
    val descripcion: String,
    val material: String,
    val descripcionMaterial: String,
    val descripcionTecnica: String,
    val precioBase: Int,
    val stockDisponible: Int,
    val imagenUrl: String
)
