package com.example.ArtesanApp.repository

import com.example.ArtesanApp.dao.FavoritoDao
import com.example.ArtesanApp.model.Favorito
import com.example.ArtesanApp.model.Producto
import kotlinx.coroutines.flow.Flow

class FavoritoRepository(
    private val favoritoDao: FavoritoDao
) {
    fun favoritosListar(usuarioId: Long): Flow<List<Producto>> {
        return favoritoDao.favoritosListar(usuarioId)
    }

    suspend fun favoritoAlmacenar(favorito: Favorito) {
        favoritoDao.favoritoAlmacenar(favorito)
    }

    suspend fun favoritoEliminar(favorito: Favorito) {
        favoritoDao.favoritoEliminar(favorito)
    }
}
