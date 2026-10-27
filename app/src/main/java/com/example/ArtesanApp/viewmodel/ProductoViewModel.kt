package com.example.ArtesanApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ArtesanApp.database.AppDatabase
import com.example.ArtesanApp.database.productosIniciales
import com.example.ArtesanApp.model.Categoria
import com.example.ArtesanApp.model.EstadoProducto
import com.example.ArtesanApp.model.OrdenCatalogo
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application)
    : AndroidViewModel(application) {
    private val repository: ProductoRepository

    val productos: StateFlow<List<Producto>>

    init {

        val productoDao = AppDatabase
            .getDatabase(application)
            .productoDao()

        repository = ProductoRepository(productoDao)

        productos = repository.productos.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        cargarDatosIniciales()
    }

    // Si la tabla está vacía (primera vez que se abre la app), guarda los productos de prueba
    private fun cargarDatosIniciales() {

        viewModelScope.launch {
            if (repository.productosContar() == 0) {
                repository.productosAlmacenarTodos(productosIniciales)
            }
        }
    }

    fun insertar(producto: Producto) {

        viewModelScope.launch {
            repository.productoAlmacenar(producto)
        }
    }

    fun actualizar(producto: Producto) {

        viewModelScope.launch {
            repository.productoActualizar(producto)
        }
    }

    // Suma o resta unidades al stock (cantidad = 1 o -1). El stock nunca baja de 0
    fun cambiarStock(producto: Producto, cantidad: Int) {

        val nuevoStock = producto.stockDisponible + cantidad

        if (nuevoStock < 0) {
            return
        }

        actualizar(
            producto.copy(
                stockDisponible = nuevoStock,
                estado = calcularEstado(producto.estado, nuevoStock)
            )
        )
    }

    fun cambiarEstado(producto: Producto, nuevoEstado: EstadoProducto) {
        actualizar(producto.copy(estado = nuevoEstado))
    }

    // Botón "Modificar" del admin en el catálogo: guarda el stock y el estado elegidos.
    // calcularEstado corrige el estado si no calza con el stock (ej: Disponible con 0 unidades -> Agotado)
    fun modificarStockYEstado(producto: Producto, nuevoStock: Int, nuevoEstado: EstadoProducto) {
        actualizar(
            producto.copy(
                stockDisponible = nuevoStock,
                estado = calcularEstado(nuevoEstado, nuevoStock)
            )
        )
    }

    // Control de stock: sin unidades queda AGOTADO y con unidades vuelve a DISPONIBLE.
    // Un producto INACTIVO se mantiene inactivo hasta que el admin lo cambie.
    fun calcularEstado(estadoActual: EstadoProducto, stock: Int): EstadoProducto {

        if (estadoActual == EstadoProducto.INACTIVO) {
            return EstadoProducto.INACTIVO
        }

        if (stock == 0) {
            return EstadoProducto.AGOTADO
        }

        return EstadoProducto.DISPONIBLE
    }

    // Aplica el buscador y los 3 filtros del catálogo.
    // Los productos INACTIVOS no se muestran al cliente, pero sí al admin (esAdmin = true).
    // categoria = null significa "Todas"
    fun filtrarCatalogo(
        productos: List<Producto>,
        texto: String,
        categoria: Categoria?,
        material: String,
        orden: OrdenCatalogo,
        esAdmin: Boolean
    ): List<Producto> {

        var resultado = productos

        if (!esAdmin) {
            resultado = resultado.filter { it.estado != EstadoProducto.INACTIVO }
        }

        if (texto.isNotBlank()) {
            resultado = resultado.filter {
                it.nombre.contains(texto, ignoreCase = true) ||
                it.descripcion.contains(texto, ignoreCase = true)
            }
        }

        if (categoria != null) {
            resultado = resultado.filter { it.categoria == categoria }
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
