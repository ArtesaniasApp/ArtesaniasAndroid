package com.example.ArtesanApp.model

import androidx.room3.Entity

@Entity(
    tableName = "favorito",
    primaryKeys = ["usuarioId", "productoId"]
)
data class Favorito(
    val usuarioId: Long,
    val productoId: Long,
    val fechaAgregado: Long = System.currentTimeMillis()
)
