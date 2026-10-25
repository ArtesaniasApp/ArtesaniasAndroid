package com.example.ArtesanApp.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "tipo_usuario")
data class TipoUsuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String
)
