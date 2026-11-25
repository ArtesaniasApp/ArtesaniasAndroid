package com.example.ArtesanApp.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.ArtesanApp.model.ItemCarrito
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun itemAlmacenar(item: ItemCarrito)

    @Query("DELETE FROM carrito WHERE usuarioId = :usuarioId AND productoId = :productoId")
    suspend fun itemEliminar(usuarioId: Long, productoId: Long)

    @Query("DELETE FROM carrito WHERE usuarioId = :usuarioId")
    suspend fun carritoVaciar(usuarioId: Long)

    @Query("SELECT * FROM carrito WHERE usuarioId = :usuarioId")
    fun carritoListar(usuarioId: Long): Flow<List<ItemCarrito>>
}
