package com.example.ArtesanApp.model

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "usuario",
    indices = [Index(value = ["email"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Long = 0,
    val tipoUsuarioId: Long,
    val nombre: String,
    val email: String,
    val telefono: String? = null,
    val direccion: String? = null,
    val password: String
)
