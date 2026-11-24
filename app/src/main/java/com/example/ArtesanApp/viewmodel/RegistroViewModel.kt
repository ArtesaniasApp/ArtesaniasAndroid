package com.example.ArtesanApp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.dto.RegistroRequest
import com.example.ArtesanApp.remote.mensajeError
import com.example.ArtesanApp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistroViewModel: ViewModel() {
    private val repository = AuthRepository()

    var nombre by mutableStateOf("")
    var email by mutableStateOf("")
    var telefono by mutableStateOf("")
    var direccion by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmarPassword by mutableStateOf("")

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

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
        if (password.length < 6) {
            return "La contraseña debe tener al menos 6 caracteres."
        }
        if (password != confirmarPassword) {
            return "Las contraseñas no coinciden."
        }
        return ""
    }

    fun registrar(onExito: () -> Unit, onError: (String) -> Unit) {
        val error = validarRegistro()

        if (error != "") {
            onError(error)
            return
        }

        val datos = RegistroRequest(
            nombre = nombre.trim(),
            email = email.trim(),
            telefono = telefono.trim(),
            password = password,
            direccion = direccion.trim()
        )

        viewModelScope.launch {
            _cargando.value = true
            try {
                repository.registrar(datos)
                onExito()
            } catch (e: Exception) {
                onError(mensajeError(e))
            }
            _cargando.value = false
        }
    }
}
