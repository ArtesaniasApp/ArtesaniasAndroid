package com.example.ArtesanApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.model.Estado
import com.example.ArtesanApp.model.EstadoPedido
import com.example.ArtesanApp.model.Solicitud
import com.example.ArtesanApp.remote.mensajeError
import com.example.ArtesanApp.repository.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PedidosAdminViewModel : ViewModel() {
    private val repository = SolicitudRepository()

    private val _pedidos = MutableStateFlow<List<Solicitud>>(emptyList())
    val pedidos: StateFlow<List<Solicitud>> = _pedidos

    private val _estados = MutableStateFlow<List<Estado>>(emptyList())

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _guardando = MutableStateFlow(false)
    val guardando: StateFlow<Boolean> = _guardando

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun mensajeMostrado() {
        _mensaje.value = ""
    }

    fun cargarPedidos() {

        viewModelScope.launch {
            _cargando.value = true
            try {
                _pedidos.value = repository.listarTodos()
                _estados.value = repository.listarEstados()
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _cargando.value = false
        }
    }

    fun cambiarEstado(pedido: Solicitud, nuevoEstado: EstadoPedido) {

        if (_guardando.value) {
            return
        }

        val estado = _estados.value.find { it.nombre == nuevoEstado.name }

        if (estado == null) {
            _mensaje.value = "No se pudieron cargar los estados. Vuelve a entrar a Pedidos."
            return
        }

        viewModelScope.launch {
            _guardando.value = true
            try {
                val actualizado = repository.cambiarEstado(pedido.id, estado.id)

                _pedidos.value = _pedidos.value.map {
                    if (it.id == actualizado.id) actualizado else it
                }
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _guardando.value = false
        }
    }

    fun siguienteEstado(estado: EstadoPedido): EstadoPedido? {
        return when (estado) {
            EstadoPedido.PENDIENTE -> EstadoPedido.RECIBIDA
            EstadoPedido.RECIBIDA -> EstadoPedido.EN_PREPARACION
            EstadoPedido.EN_PREPARACION -> EstadoPedido.DESPACHADA
            EstadoPedido.DESPACHADA -> EstadoPedido.FINALIZADA
            EstadoPedido.FINALIZADA -> null
            EstadoPedido.CANCELADA -> null
        }
    }

    fun limpiar() {
        _pedidos.value = emptyList()
    }
}
