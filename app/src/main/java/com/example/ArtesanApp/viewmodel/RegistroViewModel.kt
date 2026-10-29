package com.example.ArtesanApp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistroViewModel: ViewModel() {
    var nombre by mutableStateOf("")
    var email by mutableStateOf("")
    var telefono by mutableStateOf("")
    var direccion by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmarPassword by mutableStateOf("")

    fun cambiarNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }
    fun cambiarEmail(nuevoEmail: String) {
        email = nuevoEmail
    }
    fun cambiarTelefono(nuevoTelefono: String) {
        telefono = nuevoTelefono
    }
    fun cambiarDireccion(nuevaDireccion: String) {
        direccion = nuevaDireccion
    }
    fun cambiarPassword(nuevaPassword: String) {
        password = nuevaPassword
    }
    fun cambiarConfirmarPassword(nuevaPassword: String) {
        confirmarPassword = nuevaPassword
    }

    fun validarRegistro(): String {
        if (nombre.isBlank() || email.isBlank() || direccion.isBlank() || password.isBlank()) {
            return "Completa nombre, correo, dirección y contraseña."
        }
        if (!email.contains("@") || !email.contains(".")) {
            return "El correo electrónico no es válido."
        }
        if (password.length < 4) {
            return "La contraseña debe tener al menos 4 caracteres."
        }
        if (password != confirmarPassword) {
            return "Las contraseñas no coinciden."
        }
        return ""
    }
}
