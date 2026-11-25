package com.example.ArtesanApp.model

import androidx.room3.Entity

@Entity(
    tableName = "carrito",
    primaryKeys = ["usuarioId", "productoId"]
)
data class ItemCarrito(
    val usuarioId: Long,
    val productoId: Long,
    val cantidad: Int
)
