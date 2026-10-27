package com.example.ArtesanApp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ArtesanApp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CarritoViewModel : ViewModel() {

    private val _carrito = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val carrito = _carrito.asStateFlow()

    fun agregar(producto: Producto): Boolean {
        val cantidadActual = _carrito.value[producto.id] ?: 0

        if (cantidadActual >= producto.stockDisponible) {
            return false
        }

        _carrito.update { actual ->
            actual + (
                    producto.id to (cantidadActual + 1)
                    )
        }

        return true
    }

    // Agrega varias unidades de una vez (lo usa el detalle del producto).
    // Devuelve false si con esa cantidad se pasa del stock disponible
    fun agregarCantidad(producto: Producto, cantidad: Int): Boolean {
        val cantidadActual = _carrito.value[producto.id] ?: 0

        if (cantidadActual + cantidad > producto.stockDisponible) {
            return false
        }

        _carrito.update { actual ->
            actual + (producto.id to (cantidadActual + cantidad))
        }

        return true
    }

    fun quitar(productoId: Long) {
        _carrito.update { actual ->
            val cantidad = actual[productoId] ?: return@update actual

            if (cantidad <= 1) {
                actual - productoId
            } else {
                actual + (productoId to cantidad - 1)
            }
        }
    }

    fun eliminar(productoId: Long) {
        _carrito.update { actual ->
            actual - productoId
        }
    }

    fun vaciar() {
        _carrito.value = emptyMap()
    }

    fun incrementar(producto: Producto): Boolean {
        return agregar(producto)
    }
}