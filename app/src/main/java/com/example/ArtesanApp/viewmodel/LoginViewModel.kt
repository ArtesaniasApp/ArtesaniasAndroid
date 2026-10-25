package com.example.ArtesanApp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ArtesanApp.model.TipoUsuario
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

    val usuarioRegistrado = Usuario(nombre = "victor", email = "duoc@.cl", password = "123", tipoUsuario = TipoUsuario.ADMIN)

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