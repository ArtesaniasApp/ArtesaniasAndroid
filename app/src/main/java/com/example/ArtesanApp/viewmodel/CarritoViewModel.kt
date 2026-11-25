package com.example.ArtesanApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.database.AppDatabase
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.ItemCarrito
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.repository.CarritoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(application: Application)
    : AndroidViewModel(application) {
    private val repository: CarritoRepository

    private var usuarioId: Long = 0

    private var lectura: Job? = null

    private val _carrito = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val carrito: StateFlow<Map<Long, Int>> = _carrito

    init {

        val carritoDao = AppDatabase
            .getDatabase(application)
            .carritoDao()

        repository = CarritoRepository(carritoDao)
    }

    fun cargarCarrito(idUsuario: Long) {

        usuarioId = idUsuario

        lectura?.cancel()
        lectura = viewModelScope.launch {
            repository.carritoListar(idUsuario).collect { lista ->
                _carrito.value = lista.associate { it.productoId to it.cantidad }
            }
        }
    }

    fun agregar(producto: Producto): Boolean {
        return agregarCantidad(producto, 1)
    }

    fun agregarCantidad(producto: Producto, cantidad: Int): Boolean {
        val cantidadActual = _carrito.value[producto.id] ?: 0

        if (cantidadActual + cantidad > producto.stockDisponible) {
            return false
        }

        almacenar(producto.id, cantidadActual + cantidad)

        return true
    }

    fun incrementar(producto: Producto): Boolean {
        return agregar(producto)
    }

    fun revisarProducto(producto: Producto, cantidad: Int): String {

        if (producto.estado == EstadoProducto.INACTIVO) {
            return "Este producto ya no está disponible. Elimínalo para continuar."
        }

        if (producto.stockDisponible == 0) {
            return "Producto agotado. Elimínalo para continuar."
        }

        if (cantidad > producto.stockDisponible) {
            return "Stock insuficiente: quedan ${producto.stockDisponible}. Ajusta la cantidad."
        }

        return ""
    }

    fun productosNoDisponibles(productos: List<Producto>): List<Long> {
        return _carrito.value.keys.filter { productoId ->
            productos.none { it.id == productoId }
        }
    }

    fun quitar(productoId: Long) {
        val cantidadActual = _carrito.value[productoId] ?: 0

        if (cantidadActual <= 1) {
            eliminar(productoId)
        } else {
            almacenar(productoId, cantidadActual - 1)
        }
    }

    fun eliminar(productoId: Long) {
        viewModelScope.launch {
            repository.itemEliminar(usuarioId, productoId)
        }
    }

    fun vaciar() {
        viewModelScope.launch {
            repository.carritoVaciar(usuarioId)
        }
    }

    fun limpiar() {
        lectura?.cancel()
        usuarioId = 0
        _carrito.value = emptyMap()
    }

    private fun almacenar(productoId: Long, cantidad: Int) {

        val item = ItemCarrito(
            usuarioId = usuarioId,
            productoId = productoId,
            cantidad = cantidad
        )

        viewModelScope.launch {
            repository.itemAlmacenar(item)
        }
    }
}
