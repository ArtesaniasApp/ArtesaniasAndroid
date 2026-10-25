package com.example.ArtesanApp.repository

import com.example.ArtesanApp.dao.ProductoDao
import com.example.ArtesanApp.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(
    private val productoDao: ProductoDao
) {
    val productos: Flow<List<Producto>>
        = productoDao.productosListar()

    suspend fun productoAlmacenar(producto: Producto) {
        productoDao.productoAlmacenar(producto)
    }

    suspend fun productosAlmacenarTodos(productos: List<Producto>) {
        productoDao.productosAlmacenarTodos(productos)
    }

    suspend fun productoActualizar(producto: Producto) {
        productoDao.productoActualizar(producto)
    }

    suspend fun productosContar(): Int {
        return productoDao.productosContar()
    }
}
