package com.example.ArtesanApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.dto.ItemPedidoRequest
import com.example.ArtesanApp.dto.PedidoRequest
import com.example.ArtesanApp.model.MetodoPago
import com.example.ArtesanApp.model.Solicitud
import com.example.ArtesanApp.remote.mensajeError
import com.example.ArtesanApp.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SolicitudViewModel : ViewModel() {
    private val repository = SolicitudRepository()

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    private val _metodosPago = MutableStateFlow<List<MetodoPago>>(emptyList())
    val metodosPago: StateFlow<List<MetodoPago>> = _metodosPago

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _enviando = MutableStateFlow(false)
    val enviando: StateFlow<Boolean> = _enviando

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun mensajeMostrado() {
        _mensaje.value = ""
    }

    fun cargarSolicitudes() {

        viewModelScope.launch {
            _cargando.value = true
            try {
                _solicitudes.value = repository.listarMisPedidos()
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _cargando.value = false
        }
    }

    fun cargarMetodosPago() {

        viewModelScope.launch {
            try {
                _metodosPago.value = repository.listarMetodosPago()
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
        }
    }

    fun enviarSolicitud(
        items: List<ItemPedidoRequest>,
        metodoPago: MetodoPago,
        onExito: (Solicitud) -> Unit
    ) {

        if (_enviando.value) {
            return
        }

        viewModelScope.launch {
            _enviando.value = true
            try {
                val solicitud = repository.crear(PedidoRequest(metodoPago.id, items))
                onExito(solicitud)
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _enviando.value = false
        }
    }

    fun limpiar() {
        _solicitudes.value = emptyList()
    }
}
