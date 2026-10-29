package com.example.ArtesanApp.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoria: Categoria,
    val estado: EstadoProducto,
    val nombre: String,
    val descripcion: String,
    val material: String,
    val descripcionMaterial: String,
    val descripcionTecnica: String,
    val precioBase: Int,
    val stockDisponible: Int,
    val imagenUrl: String = ""
)
