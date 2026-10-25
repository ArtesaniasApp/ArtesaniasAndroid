package com.example.ArtesanApp.model

import androidx.room3.Entity

// Tabla favorito: qué productos guardó cada usuario.
// La clave primaria es compuesta (usuarioId + productoId): un producto no se repite para el mismo usuario.
@Entity(
    tableName = "favorito",
    primaryKeys = ["usuarioId", "productoId"]
)
data class Favorito(
    val usuarioId: Long,
    val productoId: Long,
    val fechaAgregado: Long = System.currentTimeMillis()
)
