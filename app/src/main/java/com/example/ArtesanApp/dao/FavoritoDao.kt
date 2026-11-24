package com.example.ArtesanApp.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.ArtesanApp.model.Favorito
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun favoritoAlmacenar(favorito: Favorito)

    @Delete
    suspend fun favoritoEliminar(favorito: Favorito)

    @Query("SELECT * FROM favorito WHERE usuarioId = :usuarioId ORDER BY fechaAgregado DESC")
    fun favoritosListar(usuarioId: Long): Flow<List<Favorito>>
}
