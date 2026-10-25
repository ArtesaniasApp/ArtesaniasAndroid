package com.example.ArtesanApp.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

// Tabla producto: cada artesanía del catálogo (mismos campos que el backend)
@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoria: String,              // Cerámica, Textil, Madera, Joyería o Cestería
    val estado: String,                 // DISPONIBLE, AGOTADO o INACTIVO
    val nombre: String,
    val descripcion: String,
    val material: String,               // Material corto, se usa para filtrar (Greda, Lana...)
    val descripcionMaterial: String,    // Texto largo que se muestra en el detalle
    val descripcionTecnica: String,
    val precioBase: Int,                // Pesos chilenos, sin decimales
    val stockDisponible: Int,
    val imagenUrl: String = ""
)
