package com.example.ArtesanApp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.dto.LoginRequest
import com.example.ArtesanApp.model.Usuario
import com.example.ArtesanApp.remote.Sesion
import com.example.ArtesanApp.remote.mensajeError
import com.example.ArtesanApp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val repository = AuthRepository()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun cambiarEmail(nuevoMail: String){
        email = nuevoMail
    }
    fun cambiarPassword(nuevaPassword: String) {
        password = nuevaPassword
    }
    fun mensajeMostrado() {
        _mensaje.value = ""
    }

    fun iniciarSesion(onExito: (Usuario) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            _mensaje.value = "Completa correo y contraseña."
            return
        }

        viewModelScope.launch {
            _cargando.value = true
            try {
                val respuesta = repository.login(LoginRequest(email.trim(), password))

                Sesion.token = respuesta.token
                _usuario.value = respuesta.usuario
                password = ""

                onExito(respuesta.usuario)
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _cargando.value = false
        }
    }

    fun cerrarSesion() {
        Sesion.token = ""
        _usuario.value = null
        email = ""
        password = ""
    }
}
