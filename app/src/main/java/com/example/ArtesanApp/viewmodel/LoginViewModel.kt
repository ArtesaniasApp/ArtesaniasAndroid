package com.example.ArtesanApp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ArtesanApp.model.Usuario

class LoginViewModel: ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun cambiarEmail(nuevoMail: String){
        email = nuevoMail
    }
    fun cambiarPassword(nuevaPassword: String) {
        password = nuevaPassword
    }

    // tipoUsuarioId = 2 -> ADMIN (ver tabla tipo_usuario)
    val usuarioRegistrado = Usuario(nombre = "victor", email = "@duoc.cl", password = "123", tipoUsuarioId = 2)

    fun iniciarSesion(): Boolean {
        var validador = false

        if (
            email == usuarioRegistrado.email &&
            password == usuarioRegistrado.password
        ) {
            validador = true
        }

        return validador
    }
}