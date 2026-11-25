package com.example.ArtesanApp.repository

import com.example.ArtesanApp.dao.CarritoDao
import com.example.ArtesanApp.model.ItemCarrito
import kotlinx.coroutines.flow.Flow

class CarritoRepository(
    private val carritoDao: CarritoDao
) {
    fun carritoListar(usuarioId: Long): Flow<List<ItemCarrito>> {
        return carritoDao.carritoListar(usuarioId)
    }

    suspend fun itemAlmacenar(item: ItemCarrito) {
        carritoDao.itemAlmacenar(item)
    }

    suspend fun itemEliminar(usuarioId: Long, productoId: Long) {
        carritoDao.itemEliminar(usuarioId, productoId)
    }

    suspend fun carritoVaciar(usuarioId: Long) {
        carritoDao.carritoVaciar(usuarioId)
    }
}
