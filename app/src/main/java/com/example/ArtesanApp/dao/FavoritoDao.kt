package com.example.ArtesanApp.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.ArtesanApp.model.Favorito
import com.example.ArtesanApp.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun favoritoAlmacenar(favorito: Favorito)

    @Delete
    suspend fun favoritoEliminar(favorito: Favorito)

    @Query(
        "SELECT producto.* FROM producto " +
        "INNER JOIN favorito ON producto.id = favorito.productoId " +
        "WHERE favorito.usuarioId = :usuarioId AND producto.estado != 'INACTIVO' " +
        "ORDER BY favorito.fechaAgregado DESC"
    )
    fun favoritosListar(usuarioId: Long): Flow<List<Producto>>
}
