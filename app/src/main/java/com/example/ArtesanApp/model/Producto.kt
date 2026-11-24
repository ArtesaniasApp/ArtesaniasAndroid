package com.example.ArtesanApp.model

data class Producto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val material: String,
    val descripcionMaterial: String?,
    val descripcionTecnica: String?,
    val precioBase: Int,
    val stockDisponible: Int,
    val imagenUrl: String?,
    val clasificacionId: Long,
    val clasificacion: String,
    val estado: EstadoProducto
)
