package com.example.ArtesanApp.model

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

// Tabla usuario. El email no se puede repetir (índice único)
@Entity(
    tableName = "usuario",
    indices = [Index(value = ["email"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Long = 0,
    val tipoUsuarioId: Long,            // id de la tabla tipo_usuario (1 = CLIENTE, 2 = ADMIN)
    val nombre: String,
    val email: String,
    val telefono: String? = null,
    val direccion: String? = null,      // dirección de despacho, se pide en el registro
    val password: String
)
