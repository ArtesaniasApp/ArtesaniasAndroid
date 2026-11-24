package com.example.ArtesanApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.dto.ProductoRequest
import com.example.ArtesanApp.model.Clasificacion
import com.example.ArtesanApp.model.Estado
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.OrdenCatalogo
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.remote.mensajeError
import com.example.ArtesanApp.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {
    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _clasificaciones = MutableStateFlow<List<Clasificacion>>(emptyList())
    val clasificaciones: StateFlow<List<Clasificacion>> = _clasificaciones

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

    fun cargarProductos(esAdmin: Boolean) {

        viewModelScope.launch {
            _cargando.value = true
            try {
                if (esAdmin) {
                    _productos.value = repository.listarInventario()
                } else {
                    _productos.value = repository.listarCatalogo()
                }

                _clasificaciones.value = repository.listarClasificaciones()
                _estados.value = repository.listarEstados()
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _cargando.value = false
        }
    }

    fun insertar(datos: ProductoRequest, onExito: () -> Unit) {

        viewModelScope.launch {
            _guardando.value = true
            try {
                val creado = repository.crear(datos)
                _productos.value = _productos.value + creado
                onExito()
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _guardando.value = false
        }
    }

    fun actualizar(id: Long, datos: ProductoRequest, onExito: () -> Unit) {

        viewModelScope.launch {
            _guardando.value = true
            try {
                reemplazar(repository.actualizar(id, datos))
                onExito()
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _guardando.value = false
        }
    }

    fun cambiarStock(producto: Producto, cantidad: Int) {

        val nuevoStock = producto.stockDisponible + cantidad

        if (nuevoStock < 0 || _guardando.value) {
            return
        }

        viewModelScope.launch {
            _guardando.value = true
            try {
                reemplazar(repository.cambiarStock(producto.id, nuevoStock))
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _guardando.value = false
        }
    }

    fun cambiarEstado(producto: Producto, nuevoEstado: EstadoProducto) {

        val estado = _estados.value.find { it.nombre == nuevoEstado.name }

        if (estado == null || _guardando.value) {
            return
        }

        viewModelScope.launch {
            _guardando.value = true
            try {
                reemplazar(repository.cambiarEstado(producto.id, estado.id))
            } catch (e: Exception) {
                _mensaje.value = mensajeError(e)
            }
            _guardando.value = false
        }
    }

    private fun reemplazar(actualizado: Producto) {
        _productos.value = _productos.value.map {
            if (it.id == actualizado.id) actualizado else it
        }
    }

    fun limpiar() {
        _productos.value = emptyList()
    }

    fun filtrarCatalogo(
        productos: List<Producto>,
        texto: String,
        categoria: String,
        material: String,
        orden: OrdenCatalogo
    ): List<Producto> {

        var resultado = productos

        if (texto.isNotBlank()) {
            resultado = resultado.filter {
                it.nombre.contains(texto, ignoreCase = true) ||
                it.descripcion.contains(texto, ignoreCase = true)
            }
        }

        if (categoria != "Todas") {
            resultado = resultado.filter { it.clasificacion == categoria }
        }

        if (material != "Todos") {
            resultado = resultado.filter { it.material == material }
        }

        resultado = when (orden) {
            OrdenCatalogo.DESTACADOS -> resultado
            OrdenCatalogo.PRECIO_MENOR_A_MAYOR -> resultado.sortedBy { it.precioBase }
            OrdenCatalogo.PRECIO_MAYOR_A_MENOR -> resultado.sortedByDescending { it.precioBase }
            OrdenCatalogo.MAYOR_DISPONIBILIDAD -> resultado.sortedByDescending { it.stockDisponible }
        }

        return resultado
    }
}
