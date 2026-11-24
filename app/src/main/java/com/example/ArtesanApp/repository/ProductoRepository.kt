package com.example.ArtesanApp.repository

import com.example.ArtesanApp.dto.EstadoRequest
import com.example.ArtesanApp.dto.ProductoRequest
import com.example.ArtesanApp.dto.StockRequest
import com.example.ArtesanApp.model.Clasificacion
import com.example.ArtesanApp.model.Estado
import com.example.ArtesanApp.model.Producto
import com.example.ArtesanApp.remote.ProductoApi
import com.example.ArtesanApp.remote.RetrofitClient

class ProductoRepository(
    private val api: ProductoApi = RetrofitClient.productoApi
) {
    suspend fun listarCatalogo(): List<Producto> {
        return api.listarCatalogo()
    }

    suspend fun listarInventario(): List<Producto> {
        return api.listarInventario()
    }

    suspend fun listarClasificaciones(): List<Clasificacion> {
        return api.listarClasificaciones()
    }

    suspend fun listarEstados(): List<Estado> {
        return api.listarEstados()
    }

    suspend fun crear(datos: ProductoRequest): Producto {
        return api.crear(datos)
    }

    suspend fun actualizar(id: Long, datos: ProductoRequest): Producto {
        return api.actualizar(id, datos)
    }

    suspend fun cambiarStock(id: Long, stock: Int): Producto {
        return api.cambiarStock(id, StockRequest(stock))
    }

    suspend fun cambiarEstado(id: Long, estadoId: Long): Producto {
        return api.cambiarEstado(id, EstadoRequest(estadoId))
    }
}
